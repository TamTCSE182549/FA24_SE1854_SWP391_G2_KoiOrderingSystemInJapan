package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.*;

import fall24.swp391.KoiOrderingSystem.model.response.BookingResponseDetail;

import fall24.swp391.KoiOrderingSystem.pojo.*;
import fall24.swp391.KoiOrderingSystem.repo.*;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

    @Autowired
    private ITourRepository iTourRepository;

    @Autowired
    private IKoisRepository iKoisRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private IBookingKoiDetailRepository iBookingKoiDetailRepository;

    @Autowired
    private IKoiOfFarmRepository iKoiOfFarmRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CurrencyConversionService currencyConversionService;

    @Override
    public BookingTourResponse createTourBooking(BookingTourRequest bookingTourRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if(account.getRole() == Role.CUSTOMER){
                Tours tours = iTourRepository.findById(bookingTourRequest.getTourID())
                        .orElseThrow(() -> new NotFoundEntity("Tour not found"));
                //nếu lỗi là say bye
                Bookings booking = new Bookings();
                booking.setPaymentMethod(bookingTourRequest.getPaymentMethod());
                booking.setAccount(account);
                booking.setPaymentStatus(PaymentStatus.pending);// Set default status to pending
                booking.setBookingType(BookingType.BookingForTour);
                bookingRepository.save(booking);
                //Save booking
                BookingTourDetail bookingTourDetail = new BookingTourDetail(booking, tours, bookingTourRequest.getParticipants());
                bookingTourDetail.setTotalAmount(tours.getUnitPrice() * bookingTourDetail.getParticipant());
                iBookingTourDetailRepository.save(bookingTourDetail);
                float totalBookingAmount = 0;
                List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(booking.getId());
                for(BookingTourDetail b : tourDetailOfBookingID){
                    totalBookingAmount += b.getTotalAmount();
                }
                booking.setTotalAmount(totalBookingAmount);
                booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
                bookingRepository.save(booking);
                tours.setRemaining(tours.getRemaining() - bookingTourRequest.getParticipants());
                if(tours.getRemaining()==0){
                    tours.setStatus(TourStatus.inactive);
                }
                booking.setCreatedBy(account);
                iTourRepository.save(tours);
                BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
                bookingTourResponse.setCustomerID(account.getId());
                bookingTourResponse.setNameCus(account.getFirstName() + " " + account.getLastName());
                bookingTourResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
                return bookingTourResponse;
            } else {
                throw new NotCreateException("Create Tour Booking Only Role Customer");
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

//    @Override
//    public List<Bookings> getTourBooking(Long accountID) {
//        return bookingRepository.listTourBookingByID(accountID);
//    }

    @Override
    public List<BookingTourResponse> bookingForTour() {
        Account account = authenticationService.getCurrentAccount();
        List<Bookings> bookingTourResponses = null;
        if (account.getRole() == Role.MANAGER){
            bookingTourResponses = bookingRepository.listBookingForTour();
        } else {
            throw new NotFoundEntity("Account not FOUND");
        }

        return bookingTourResponses.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        }).toList();
    }

    @Override
    public List<BookingTourResponse> getTourBookingResponse() {
        Account account = authenticationService.getCurrentAccount();
        List<Bookings> bookingsList = bookingRepository.listTourBookingByID(account.getId());
        return bookingsList.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        }).toList();
    }

    @Override
    public List<BookingTourResponse> getBookingResponseForDashBoard() {
        Account account = authenticationService.getCurrentAccount();
        if(account.getRole() != Role.MANAGER){
            throw new NotReadException("Your role cannot access");
        }
        List<Bookings> bookingsList = bookingRepository.listBookingForDashBoard();
        return bookingsList.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        }).toList();
    }

//    @Override
//    public Bookings updateTourBooking(Long id, Bookings bookingUpdateDetail) {
//        try {
//            Account account = authenticationService.getCurrentAccount();
//            if (account.getRole() != Role.MANAGER){
//                throw new NotUpdateException("Your Role Cannot Access");
//            }
//            Optional<Bookings> existingBooking = bookingRepository.findById(id);
//            if (existingBooking.isPresent()) {
//                Bookings bookingToUpdate = existingBooking.get();
//                // Update fields except paymentStatus if it's pending or cancelled
//                if (bookingToUpdate.getPaymentStatus() != PaymentStatus.pending &&
//                        bookingToUpdate.getPaymentStatus() != PaymentStatus.cancelled) {
//                    bookingToUpdate.setPaymentStatus(bookingUpdateDetail.getPaymentStatus());
//                }
//                bookingToUpdate.setPaymentMethod(bookingToUpdate.getPaymentMethod());
//                bookingToUpdate.setDiscountAmount(bookingUpdateDetail.getDiscountAmount());
//                bookingToUpdate.setVat(bookingUpdateDetail.getVat());
//                bookingToUpdate.setVatAmount(bookingToUpdate.getVat() * bookingToUpdate.getTotalAmount());
//                bookingToUpdate.setUpdatedBy(account);
//                float totalBookingAmount = 0;
//                List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(id);
//                for(BookingTourDetail b : tourDetailOfBookingID){
//                    totalBookingAmount += b.getTotalAmount();
//                }
//                bookingToUpdate.setTotalAmount(totalBookingAmount);
//                bookingToUpdate.setTotalAmountWithVAT(bookingToUpdate.getTotalAmount() + bookingToUpdate.getVatAmount() - bookingToUpdate.getDiscountAmount());
//                return bookingRepository.save(bookingToUpdate);
//            } else {
//                throw new NotUpdateException("Update booking id " + id + " failed");
//            }
//        } catch (Exception e) {
//            throw new GenericException(e.getMessage());
//        }
//    }

    @Override
    public BookingTourResponse updateTourBookingResponse(Bookings bookingDetails) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() != Role.MANAGER){
                throw new NotUpdateException("Your Role Cannot Access");
            }
            Bookings booking = bookingRepository.findById(bookingDetails.getId())
                    .orElseThrow(() -> new NotFoundEntity("Booking not exists"));
            booking.setVat(bookingDetails.getVat());
            booking.setVatAmount(booking.getVat() * booking.getTotalAmount());
            booking.setDiscountAmount(bookingDetails.getDiscountAmount());
            float totalBookingAmount = 0;
            List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(booking.getId());
            for(BookingTourDetail b : tourDetailOfBookingID){
                totalBookingAmount += b.getTotalAmount();
            }
            booking.setTotalAmount(totalBookingAmount);
            booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
            booking.setPaymentMethod(bookingDetails.getPaymentMethod());
            booking.setUpdatedBy(account);
            bookingRepository.save(booking);
            BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
            bookingTourResponse.setCreatedBy(booking.getCreatedBy().getFirstName() +  " " + booking.getCreatedBy().getLastName());
            bookingTourResponse.setUpdatedBy(booking.getUpdatedBy().getFirstName() +  " " + booking.getUpdatedBy().getLastName());
            return modelMapper.map(booking, BookingTourResponse.class);
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public BookingTourResponse bookingUpdatePaymentMethod(BookingUpdateRequestCus bookingUpdateRequestCus) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() != Role.CUSTOMER){
                throw new NotUpdateException("Your Role cannot access");
            }
            Bookings bookings = bookingRepository.findById(bookingUpdateRequestCus.getBookingID())
                    .orElseThrow(() -> new NotUpdateException("Booking ID not FOUND"));
            bookings.setPaymentMethod(bookingUpdateRequestCus.getPaymentMethod());
            bookingRepository.save(bookings);
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        } catch (Exception e) {
            throw new NotUpdateException(e.getMessage());
        }
    }

    @Override
    public BookingTourResponse responseUpdateForStaff(BookingUpdateRequestStaff bookingUpdateRequestStaff) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() != Role.MANAGER){
                throw new NotUpdateException("Your Role cannot access");
            }
            Bookings bookings = bookingRepository.findById(bookingUpdateRequestStaff.getBookingID())
                    .orElseThrow(() -> new NotFoundEntity("Booking ID not FOUND"));
            bookings.setPaymentMethod(bookingUpdateRequestStaff.getPaymentMethod());
            bookings.setPaymentStatus(bookingUpdateRequestStaff.getPaymentStatus());
            if(bookings.getPaymentStatus() == PaymentStatus.complete){
                bookings.setPaymentDate(LocalDateTime.now());
            }
            bookings.setVat(bookingUpdateRequestStaff.getVat());
            bookings.setDiscountAmount(bookingUpdateRequestStaff.getDiscountAmount());
            bookings.setUpdatedBy(account);
            bookings.setDiscountAmount(bookingUpdateRequestStaff.getDiscountAmount());
            bookings.setVatAmount(bookingUpdateRequestStaff.getVat() * (bookings.getTotalAmount() - bookingUpdateRequestStaff.getDiscountAmount()));
            bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
            bookingRepository.save(bookings);
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            bookingTourResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    //delete booking Vinh Vien only for MANAGER
    @Override
    public void deleteBookingForManager(Long id) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!=Role.MANAGER){
                throw new NotCreateException("Your Role cannot access");
            }
            Bookings bookings = bookingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEntity("Booking not FOUND to DELETE"));
            if (bookings.getPaymentStatus()==PaymentStatus.cancelled){
                iBookingTourDetailRepository.deleteBTDByBooking_Id(bookings.getId());
                bookingRepository.deleteById(bookings.getId());
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public BookingTourResponse createKoiBooking(BookingKoiRequest bookingKoiRequest,Long bookingId) {
        try{
            Account account = authenticationService.getCurrentAccount();
           if(account.getRole() ==Role.SALES_STAFF){
                KoiFarms koiFarms = iKoiFarmsRepository.findById(bookingKoiRequest.getFarmId())
                        .orElseThrow(() -> new NotFoundEntity("Koi farm not found"));

                Bookings bookingTour = bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new NotFoundEntity("Booking Tour not found"));

                Bookings booking = new Bookings();
                booking.setPaymentMethod(bookingKoiRequest.getPaymentMethod());
                booking.setCreatedBy(account);
                booking.setPaymentStatus(PaymentStatus.pending);
                booking.setBookingType(BookingType.BookingForKoi);
                booking.setVat(bookingKoiRequest.getVat());
                booking.setDiscountAmount(bookingKoiRequest.getDiscountAmount());
                bookingRepository.save(booking);

                float totalBookingAmount = 0;
                for (BookingKoiDetailRequest detailRequest : bookingKoiRequest.getDetails()) {
//                    Kois kois = iKoisRepository.findByFarmIdAndKoiId(detailRequest.getKoiId(),bookingKoiRequest.getFarmId());
                    Kois kois =iKoisRepository.findById(detailRequest.getKoiId())
                            .orElseThrow(() -> new NotFoundEntity("Not found Koi"));
                    BookingKoiDetail bookingKoiDetail = new BookingKoiDetail(booking, kois, detailRequest.getQuantity(), detailRequest.getUnitPrice());
                    float totalAmount = detailRequest.getUnitPrice() * detailRequest.getQuantity();
                    bookingKoiDetail.setTotalAmount(totalAmount);
                    iBookingKoiDetailRepository.save(bookingKoiDetail);

                    totalBookingAmount += totalAmount;
                }

                    booking.setTotalAmount(totalBookingAmount);
                    booking.setVatAmount(bookingKoiRequest.getVat()*(booking.getTotalAmount()-bookingKoiRequest.getDiscountAmount()));
                    booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
                    booking.setAccount(bookingTour.getAccount());
                    bookingRepository.save(booking);

                    BookingTourResponse bookingResponse = modelMapper.map(booking, BookingTourResponse.class);
                    bookingResponse.setCustomerID(bookingTour.getCreatedBy().getId());
                    bookingResponse.setNameCus(bookingTour.getAccount().getFirstName()+" "+bookingTour.getAccount().getLastName());
                    bookingResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
                    if (booking.getUpdatedBy() == null) {
                    bookingResponse.setUpdatedBy("");
                     }
                    return bookingResponse;
            }else{
               throw new NotCreateException("Create Booking Only Role STAFF");
            }
        }catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }


    @Override
    public BookingTourResponse updateKoiBooking(Long id, BookingUpdate bookingUpdate) {
       try{
           Account account =authenticationService.getCurrentAccount();
           if(account.getRole()!=Role.SALES_STAFF) {
               throw new NotUpdateException("Your role cannot access");
           }
               Bookings bookings = bookingRepository.findById(id)
                       .orElseThrow(() -> new NotFoundEntity("Not Found Booking"));
                   if (bookings.getPaymentStatus() != PaymentStatus.cancelled) {
                        bookings.setPaymentMethod(bookingUpdate.getPaymentMethod());
                        bookings.setDiscountAmount(bookingUpdate.getDiscountAmount());
                        bookings.setVat(bookingUpdate.getVat());
                        bookings.setVatAmount(bookingUpdate.getVat() * (bookings.getTotalAmount()-bookingUpdate.getDiscountAmount()));
                        bookings.setUpdatedBy(authenticationService.getCurrentAccount());
                   float totalBookingKoiAmount = 0;
                   List<BookingKoiDetail> bookingKoiDetails = iBookingKoiDetailRepository.showDetailOfBookingID(id);
                   for (BookingKoiDetail b : bookingKoiDetails) {
                       totalBookingKoiAmount += b.getTotalAmount();
                   }

           bookings.setTotalAmount(totalBookingKoiAmount);
           bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
           bookingRepository.save(bookings);
           BookingTourResponse bookingResponse = modelMapper.map(bookings,BookingTourResponse.class);
           bookingResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
           bookingResponse.setCustomerID(bookings.getAccount().getId());
           bookingResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
           bookingResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
                   return bookingResponse;
               } else {
                   throw new NotUpdateException("Update booing id" + id + "failed");
               }

       }catch (NotUpdateException e){
           throw new GenericException(e.getMessage());
       }
    }


    @Override
    public List<BookingTourResponse> getKoiBookingById(Long accountID) {
        Account account = authenticationService.getCurrentAccount();
        List<Bookings> bookingsList = bookingRepository.listKoiBooking(accountID);
        return bookingsList.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        }).toList();
    }


    @Override
    public List<BookingTourResponse> getKoiBooking() {
        Account account = authenticationService.getCurrentAccount();
        List<Bookings> bookingTourResponses = null;
       if (account.getRole() == Role.SALES_STAFF || account.getRole() == Role.CUSTOMER){
            bookingTourResponses = bookingRepository.listBookingForKoi();
      } else {
            throw new NotFoundEntity("Account not FOUND");        }

        return bookingTourResponses.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            if (bookings.getUpdatedBy() == null) {
                bookingTourResponse.setUpdatedBy("");
            } else {
                bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName() + " " + bookings.getUpdatedBy().getLastName());
            }

            if (bookings.getCreatedBy() == null) {
                bookingTourResponse.setCreatedBy("");
            } else {
                bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName() + " " + bookings.getCreatedBy().getLastName());
            }
            bookingTourResponse.setCustomerID(bookings.getAccount().getId());
            bookingTourResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingTourResponse;
        }).toList();
    }


    @Override
    public BookingResponseDetail viewDetailBooking(Long bookingId) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() != Role.SALES_STAFF) {
                throw new NotUpdateException("Your role cannot access");
            }
            Bookings bookings = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new NotFoundEntity("Not Found Booking"));
            BookingResponseDetail bookingResponse = modelMapper.map(bookings, BookingResponseDetail.class);
            bookingResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
            bookingResponse.setCustomerID(bookings.getAccount().getId());
            bookingResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
            bookingResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingResponse;
        }catch (Exception e){
            throw new NotFoundEntity(e.getMessage());
        }
    }

    //Delete Booking for customer
    public BookingTourResponse deleteBookingResponse(Long bookingID) {
        Bookings booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new NotFoundEntity("Booking not exist"));
        Account account = authenticationService.getCurrentAccount();
        if(account.getRole() != Role.CUSTOMER) {
            throw new NotDeleteException("Your role cannot delete");
        }
        if(booking.getPaymentStatus()!=PaymentStatus.pending && booking.getPaymentStatus()!=PaymentStatus.processing){
            throw new NotDeleteException("Your cannot delete this booking because it processing");
        }
        booking.setPaymentStatus(PaymentStatus.cancelled);
        booking.setUpdatedBy(account);
        bookingRepository.save(booking);
        BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
        if (booking.getUpdatedBy() == null) {
            bookingTourResponse.setUpdatedBy("");
        } else {
            bookingTourResponse.setUpdatedBy(booking.getUpdatedBy().getFirstName() + " " + booking.getUpdatedBy().getLastName());
        }

        if (booking.getCreatedBy() == null) {
            bookingTourResponse.setCreatedBy("");
        } else {
            bookingTourResponse.setCreatedBy(booking.getCreatedBy().getFirstName() + " " + booking.getCreatedBy().getLastName());
        }
        bookingTourResponse.setCustomerID(booking.getAccount().getId());
        bookingTourResponse.setNameCus(booking.getAccount().getFirstName() + " " + booking.getAccount().getLastName());
        return modelMapper.map(booking, BookingTourResponse.class);
    }

    @Override

    public BookingTourResponse updateStastus(Long bookingId) {
        try{
            Account account = authenticationService.getCurrentAccount();
            if(account.getRole()!= Role.SALES_STAFF){
                throw new NotUpdateException("Your Role cannot access");
            }
            Bookings bookings = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new NotFoundEntity("Not Found Booking"));
            if(bookings.getPaymentStatus() == PaymentStatus.pending){
                bookings.setPaymentStatus(PaymentStatus.processing);
            }
            bookings.setUpdatedBy(authenticationService.getCurrentAccount());
            bookingRepository.save(bookings);
            BookingTourResponse bookingResponse = modelMapper.map(bookings,BookingTourResponse.class);
            bookingResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
            bookingResponse.setCustomerID(bookings.getAccount().getId());
            bookingResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
            bookingResponse.setNameCus(bookings.getAccount().getFirstName() + " " + bookings.getAccount().getLastName());
            return bookingResponse;
        }catch (Exception e){
            throw new NotUpdateException(e.getMessage());
        }
    }


@Override
    public String createUrl(Long bookingId) throws  Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        Bookings booking = bookingRepository.findBookingsById(bookingId);
        float money = currencyConversionService.convertUsdToVnd(booking.getTotalAmountWithVAT()) *100;
        String amount = String.valueOf((int) money);

        String tmnCode = "JH4XT293";
        String secretKey = "S5X7K9OKZQLCE1Z0VI2LYOV1SLEWTSZP";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:3000/paymentsuccess";
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", booking.getId().toString());
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + booking.getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    @Override
    public void updatePayment(PaymentRequest paymentRequest) {
        String text = paymentRequest.getVnp_OrderInfo();
        String bookingId = text.split(": ")[1];
        // Tạo pattern để tìm số sau "ma GD:"
        Bookings bookings = bookingRepository.findBookingsById(Long.parseLong(bookingId));
        if(bookings == null) {
            throw new NotFoundEntity("Not found Booking");
        }
        if (paymentRequest.getVnp_ResponseCode().equals("00")){
            bookings.setPaymentStatus(PaymentStatus.complete);
            bookings.setPaymentDate(LocalDateTime.now());
        }   else {
            bookings.setPaymentStatus(PaymentStatus.cancelled);
        }
        //luu them ngay
        bookingRepository.save(bookings);
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
