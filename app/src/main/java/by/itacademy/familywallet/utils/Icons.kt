package by.itacademy.familywallet.utils

import by.itacademy.familywallet.R
import by.itacademy.familywallet.model.IconModel

enum class Icons(val imageRes: Int) {
    MEDICAL_SERVICES(R.drawable.ic_baseline_medical_services),
    CLEAN_HANDS(R.drawable.ic_baseline_clean_hands),
    BACK_HAND(R.drawable.ic_baseline_back_hand),
    ECO(R.drawable.ic_baseline_eco),
    EMERGENCY(R.drawable.ic_baseline_emergency),
    INSERT_PHOTO(R.drawable.ic_baseline_insert_photo),
    RSS_FEED(R.drawable.ic_baseline_rss_feed),
    SAILING(R.drawable.ic_baseline_sailing),
    SPORTS_ESPORTS(R.drawable.ic_baseline_sports_esports),
    STOREFRONT(R.drawable.ic_baseline_storefront),
    WIFI(R.drawable.ic_baseline_wifi),
    ACCOUNT_BALANCE(R.drawable.ic_baseline_account_balance),
    AIRPLANE_MODE_ACTIVE(R.drawable.ic_baseline_airplanemode_active),
    ANCHOR(R.drawable.ic_baseline_anchor),
    APARTMENT(R.drawable.ic_baseline_apartment),
    ATTACH_FILE(R.drawable.ic_baseline_attach_file),
    AUDIO_TRACK(R.drawable.ic_baseline_audiotrack),
    CHANGING_STATION(R.drawable.ic_baseline_baby_changing_station),
    BEACH_ACCESS(R.drawable.ic_baseline_beach_access),
    BED(R.drawable.ic_baseline_bed),
    BIOTECH(R.drawable.ic_baseline_biotech),
    BRUSH(R.drawable.ic_baseline_brush),
    BLENDER(R.drawable.ic_baseline_blender),
    BUILD(R.drawable.ic_baseline_build),
    BUNGALOW(R.drawable.ic_baseline_bungalow),
    BUSINESS_CENTER(R.drawable.ic_baseline_business_center),
    CAKE(R.drawable.ic_baseline_cake),
    CELEBRATION(R.drawable.ic_baseline_celebration),
    CHAIR(R.drawable.ic_baseline_chair),
    CHECK_ROOM(R.drawable.ic_baseline_checkroom),
    FRIENDLY(R.drawable.ic_baseline_child_friendly),
    COFFEE(R.drawable.ic_baseline_coffee),
    COLOR_LENS(R.drawable.ic_baseline_color_lens),
    COMMUTE(R.drawable.ic_baseline_commute),
    COMPUTER(R.drawable.ic_baseline_computer),
    CONTENT_CUT(R.drawable.ic_baseline_content_cut),
    CORONAVIRUS(R.drawable.ic_baseline_coronavirus),
    COTTAGE(R.drawable.ic_baseline_cottage),
    DARK_MODE(R.drawable.ic_baseline_dark_mode),
    DELETE(R.drawable.ic_baseline_delete),
    DELIVERY_DINING(R.drawable.ic_baseline_delivery_dining),
    DESKTOP_MAC(R.drawable.ic_baseline_desktop_mac),
    DINNER_DINING(R.drawable.ic_baseline_dinner_dining),
    DIRECTIONS_BIKE(R.drawable.ic_baseline_directions_bike),
    DIRECTIONS_BOAT_FILLED(R.drawable.ic_baseline_directions_boat_filled),
    DIRECTIONS_BUS(R.drawable.ic_baseline_directions_bus),
    DIRECTIONS_CAR(R.drawable.ic_baseline_directions_car),
    DIRECTIONS_TRANSIT(R.drawable.ic_baseline_directions_transit),
    DOWNHILL_SKIING(R.drawable.ic_baseline_downhill_skiing),
    DRY_CLEANING(R.drawable.ic_baseline_dry_cleaning),
    ELECTRICAL_SERVICES(R.drawable.ic_baseline_electrical_services),
    EMOJI_EVENTS(R.drawable.ic_baseline_emoji_events),
    EMOJI_OBJECKTS(R.drawable.ic_baseline_emoji_objects),
    EURO(R.drawable.ic_baseline_euro),
    EXPLORE(R.drawable.ic_baseline_explore),
    FACE(R.drawable.ic_baseline_face),
    FAMILY_ROOM(R.drawable.ic_baseline_family_restroom),
    FASTFOOD(R.drawable.ic_baseline_fastfood),
    FAVORITE_BORDER(R.drawable.ic_baseline_favorite_border),
    FILTER_HDR(R.drawable.ic_baseline_filter_hdr),
    FILTER_VINTAGE(R.drawable.ic_baseline_filter_vintage),
    FITNESS_CENTER(R.drawable.ic_baseline_fitness_center),
    GROUPS(R.drawable.ic_baseline_groups),
    HANDYMAN(R.drawable.ic_baseline_handyman),
    HIKING(R.drawable.ic_baseline_hiking),
    ICECREAM(R.drawable.ic_baseline_icecream),
    LINQUOR(R.drawable.ic_baseline_liquor),
    LOCAL_DINING(R.drawable.ic_baseline_local_dining),
    LOCAL_GAS_STATION(R.drawable.ic_baseline_local_gas_station),
    LOCAL_GROCERY_STORE(R.drawable.ic_baseline_local_grocery_store),
    LOCAL_PHONE(R.drawable.ic_baseline_local_phone),
    PERSON(R.drawable.ic_baseline_person),
    PETS(R.drawable.ic_baseline_pets),
    PHONE_ANDROID(R.drawable.ic_baseline_phone_android),
    REAL_ESTATE_AGENT(R.drawable.ic_baseline_real_estate_agent),
    SCHOOL(R.drawable.ic_baseline_school),
    SPA(R.drawable.ic_baseline_spa),
    THEATER_COMEDY(R.drawable.ic_baseline_theater_comedy),
    VOLUNTEER_ACTIVISM(R.drawable.ic_baseline_volunteer_activism),
    WATCH(R.drawable.ic_baseline_watch),
    DEVICES_OTHER(R.drawable.ic_baseline_devices_other),
    ENGINEERING(R.drawable.ic_baseline_engineering),
    EQUALIZER(R.drawable.ic_baseline_equalizer),
    YARD(R.drawable.ic_baseline_yard),
    MDI_CAR_ARROW(R.drawable.ic_mdi_car_arrow_right),
    MDI_CAR_COG(R.drawable.ic_mdi_car_cog),
    MDI_CASH(R.drawable.ic_mdi_cash),
    MDI_CAT(R.drawable.ic_mdi_cat),
    MDI_DANCE_BALLROOM(R.drawable.ic_mdi_dance_ballroom),
    MDI_DOG(R.drawable.ic_mdi_dog),
    MDI_FACE_WOMAN_SHIMMER(R.drawable.ic_mdi_face_woman_shimmer),
    MDI_FLOOR_LAMP(R.drawable.ic_mdi_floor_lamp),
    MDI_FOOT_PRINT(R.drawable.ic_mdi_foot_print),
    MDI_FORMAT_PAINT(R.drawable.ic_mdi_format_paint),
    MDI_LIPSTICK(R.drawable.ic_mdi_lipstick),
    MDI_TIE(R.drawable.ic_mdi_tie),
    MDI_WEIGHT_LIFTER(R.drawable.ic_mdi_weight_lifter),
    VPN_KEY(R.drawable.ic_baseline_vpn_key);

    companion object{
        fun getIcons(): List<IconModel> {
            val resultList = mutableListOf<IconModel>()
            Icons.values().forEach { resultList.add(IconModel(it.name, it.imageRes)) }
            return resultList
        }
    }
}