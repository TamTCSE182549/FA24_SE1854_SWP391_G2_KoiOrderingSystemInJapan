package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.*;
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

import java.util.List;
import java.util.Optional;

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
    private IBookingKoiDetailRepository iBookingKoiDetailRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

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
                iTourRepository.save(tours);
                BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
                bookingTourResponse.setCustomerID(account.getId());
                bookingTourResponse.setNameCus(account.getFirstName() + " " + account.getLastName());
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
//            if (account.getRole() != Role.MANAGER){
//                throw new NotUpdateException("Your Role cannot access");
//            }
            Bookings bookings = bookingRepository.findById(bookingUpdateRequestStaff.getBookingID())
                    .orElseThrow(() -> new NotFoundEntity("Booking ID not FOUND"));
            bookings.setPaymentMethod(bookingUpdateRequestStaff.getPaymentMethod());
            bookings.setPaymentStatus(bookingUpdateRequestStaff.getPaymentStatus());
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
//            if(account.getRole() ==Role.SALES_STAFF){
//                Kois kois = iKoisRepository.findById(bookingKoiRequest.getKoiId())
//                        .orElseThrow(() -> new NotFoundEntity("Koi Tour not found"));

                Bookings bookingTour = bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new NotFoundEntity("Booking Tour not found"));

                Bookings booking = new Bookings();
                booking.setPaymentMethod(bookingKoiRequest.getPaymentMethod());
                booking.setCreatedBy(account);
                booking.setPaymentStatus(PaymentStatus.pending);
                booking.setBookingType(BookingType.BookingForKoi);
                bookingRepository.save(booking);


//                    BookingKoiDetail bookingKoiDetail = new BookingKoiDetail(booking, kois, bookingKoiRequest.getQuantity(), bookingKoiRequest.getUnitPrice());
//                    bookingKoiDetail.setTotalAmount(bookingKoiRequest.getUnitPrice() * bookingKoiRequest.getQuantity());
//                    iBookingKoiDetailRepository.save(bookingKoiDetail);
                float totalBookingAmount = 0;
                for (BookingKoiDetailRequest detailRequest : bookingKoiRequest.getDetails()) {
                    Kois kois = iKoisRepository.findById(detailRequest.getKoiId())
                            .orElseThrow(() -> new NotFoundEntity("Koi not found"));
                    BookingKoiDetail bookingKoiDetail = new BookingKoiDetail(booking, kois, detailRequest.getQuantity(), detailRequest.getUnitPrice());
                    float totalAmount = detailRequest.getUnitPrice() * detailRequest.getQuantity();
                    bookingKoiDetail.setTotalAmount(totalAmount);
                    iBookingKoiDetailRepository.save(bookingKoiDetail);

                    totalBookingAmount += totalAmount;
                }

//                    float totalBookingAmount = 0;
//                    List<BookingKoiDetail> koiDetailOfBookingID = iBookingKoiDetailRepository.showDetailOfBookingID(booking.getId());
//                    for (BookingKoiDetail b : koiDetailOfBookingID) {
//                        totalBookingAmount += b.getTotalAmount();
//                    }
                    booking.setTotalAmount(totalBookingAmount);
                    booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
                    booking.setAccount(bookingTour.getCreatedBy());
                    bookingRepository.save(booking);

                    BookingTourResponse bookingResponse = modelMapper.map(booking, BookingTourResponse.class);
                    bookingResponse.setCustomerID(bookingTour.getCreatedBy().getId());
                    bookingResponse.setNameCus(bookingTour.getAccount().getFirstName()+" "+bookingTour.getAccount().getLastName());
                    bookingResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
                    return bookingResponse;
//            }else{
//                throw new NotCreateException("Create Booking Only Role STAFF");
//            }
        }catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public Bookings updateKoiBooking(Long id, Bookings bookingDetails) {
       try{
            Optional<Bookings> existingBooking =bookingRepository.findById(id);
            if(existingBooking.isPresent()){
                Bookings bookingKoiToUpdate =existingBooking.get();
                if(bookingKoiToUpdate.getPaymentStatus() !=PaymentStatus.cancelled &&
                        bookingKoiToUpdate.getPaymentStatus()!=PaymentStatus.pending){
                    bookingKoiToUpdate.setPaymentStatus((bookingDetails.getPaymentStatus()));
                }

                bookingKoiToUpdate.setPaymentMethod(bookingDetails.getPaymentMethod());
                bookingKoiToUpdate.setDiscountAmount(bookingDetails.getDiscountAmount());
                bookingKoiToUpdate.setVat(bookingDetails.getVat());
                bookingKoiToUpdate.setVatAmount(bookingKoiToUpdate.getVat()* bookingKoiToUpdate.getTotalAmount());
                bookingKoiToUpdate.setUpdatedBy(authenticationService.getCurrentAccount());
                float totalBookingKoiAmount =0;
                List<BookingKoiDetail> bookingKoiDetails = iBookingKoiDetailRepository.showDetailOfBookingID(id);
                for(BookingKoiDetail b: bookingKoiDetails){
                    totalBookingKoiAmount +=b.getTotalAmount();
                }

                bookingKoiToUpdate.setTotalAmount(totalBookingKoiAmount);
                bookingKoiToUpdate.setTotalAmountWithVAT(bookingKoiToUpdate.getTotalAmount()+bookingKoiToUpdate.getVatAmount()-bookingKoiToUpdate.getDiscountAmount());
                return bookingRepository.save(bookingKoiToUpdate);
            }else{
                throw new NotUpdateException("Update booing id"+id+"failed");
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
//        if (account.getRole() == Role.MANAGER){
            bookingTourResponses = bookingRepository.listBookingForKoi();
//       } else {
//            throw new NotFoundEntity("Account not FOUND");
//        }

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

    public BookingTourResponse deleteBookingResponse(Long bookingID) {
        Bookings booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new NotFoundEntity("Booking not exist"));
        Account account = authenticationService.getCurrentAccount();
        if(booking.getPaymentStatus()!=PaymentStatus.pending){
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

}
