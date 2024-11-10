package fall24.swp391.KoiOrderingSystem.enums;

public enum Route {
    TAKE_KOI_AT_FARM,
    DEPARTING_FROM_JAPAN,
    ARRIVED_YOUR_COUNTRY,
    IN_LOCAL_TRANSIT,
    DELIVERED_TO_CUSTOMER;
    public boolean canTransitionTo(Route nextRoute) {
        return this.ordinal() + 1 == nextRoute.ordinal();
    }
}
