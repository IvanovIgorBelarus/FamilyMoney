package by.itacademy.familywallet.viewmodel

import by.itacademy.familywallet.R
import by.itacademy.familywallet.utils.ProgressBarUtils.isLoading

class IconChooseViewModel : BaseViewModel() {

    override fun getData() {
        isLoading.set(true)
        mutableLiveData.value = getIcons()
        isLoading.set(false)
    }

    private fun getIcons(): List<Int> {
        val resultList= mutableListOf<Int>()
        resultList.add(R.drawable.ic_baseline_account_balance)
        resultList.add(R.drawable.ic_baseline_airplanemode_active)
        resultList.add(R.drawable.ic_baseline_anchor)
        resultList.add(R.drawable.ic_baseline_apartment)
        resultList.add(R.drawable.ic_baseline_attach_file)
        resultList.add(R.drawable.ic_baseline_audiotrack)
        resultList.add(R.drawable.ic_baseline_baby_changing_station)
        resultList.add(R.drawable.ic_baseline_beach_access)
        resultList.add(R.drawable.ic_baseline_bed)
        resultList.add(R.drawable.ic_baseline_biotech)
        resultList.add(R.drawable.ic_baseline_brush)
        resultList.add(R.drawable.ic_baseline_blender)
        resultList.add(R.drawable.ic_baseline_build)
        resultList.add(R.drawable.ic_baseline_bungalow)
        resultList.add(R.drawable.ic_baseline_business_center)
        resultList.add(R.drawable.ic_baseline_cake)
        resultList.add(R.drawable.ic_baseline_celebration)
        resultList.add(R.drawable.ic_baseline_chair)
        resultList.add(R.drawable.ic_baseline_checkroom)
        resultList.add(R.drawable.ic_baseline_child_friendly)
        resultList.add(R.drawable.ic_baseline_coffee)
        resultList.add(R.drawable.ic_baseline_color_lens)
        resultList.add(R.drawable.ic_baseline_commute)
        resultList.add(R.drawable.ic_baseline_computer)
        resultList.add(R.drawable.ic_baseline_content_cut)
        resultList.add(R.drawable.ic_baseline_coronavirus)
        resultList.add(R.drawable.ic_baseline_cottage)
        resultList.add(R.drawable.ic_baseline_dark_mode)
        resultList.add(R.drawable.ic_baseline_delete)
        resultList.add(R.drawable.ic_baseline_delivery_dining)
        resultList.add(R.drawable.ic_baseline_desktop_mac)
        resultList.add(R.drawable.ic_baseline_dinner_dining)
        resultList.add(R.drawable.ic_baseline_directions_bike)
        resultList.add(R.drawable.ic_baseline_directions_boat_filled)
        resultList.add(R.drawable.ic_baseline_directions_bus)
        resultList.add(R.drawable.ic_baseline_directions_car)
        resultList.add(R.drawable.ic_baseline_directions_transit)
        resultList.add(R.drawable.ic_baseline_downhill_skiing)
        resultList.add(R.drawable.ic_baseline_dry_cleaning)
        resultList.add(R.drawable.ic_baseline_electrical_services)
        resultList.add(R.drawable.ic_baseline_emoji_events)
        resultList.add(R.drawable.ic_baseline_emoji_objects)
        resultList.add(R.drawable.ic_baseline_euro)
        resultList.add(R.drawable.ic_baseline_explore)
        resultList.add(R.drawable.ic_baseline_face)
        resultList.add(R.drawable.ic_baseline_family_restroom)
        resultList.add(R.drawable.ic_baseline_fastfood)
        resultList.add(R.drawable.ic_baseline_favorite_border)
        resultList.add(R.drawable.ic_baseline_filter_hdr)
        resultList.add(R.drawable.ic_baseline_filter_vintage)
        resultList.add(R.drawable.ic_baseline_fitness_center)
        resultList.add(R.drawable.ic_baseline_groups)
        resultList.add(R.drawable.ic_baseline_handyman)
        resultList.add(R.drawable.ic_baseline_hiking)
        resultList.add(R.drawable.ic_baseline_icecream)
        resultList.add(R.drawable.ic_baseline_liquor)
        resultList.add(R.drawable.ic_baseline_local_dining)
        resultList.add(R.drawable.ic_baseline_local_gas_station)
        resultList.add(R.drawable.ic_baseline_local_grocery_store)
        resultList.add(R.drawable.ic_baseline_local_phone)
        resultList.add(R.drawable.ic_baseline_person)
        resultList.add(R.drawable.ic_baseline_pets)
        resultList.add(R.drawable.ic_baseline_phone_android)
        resultList.add(R.drawable.ic_baseline_real_estate_agent)
        resultList.add(R.drawable.ic_baseline_school)
        resultList.add(R.drawable.ic_baseline_spa)
        resultList.add(R.drawable.ic_baseline_theater_comedy)
        resultList.add(R.drawable.ic_baseline_volunteer_activism)
        resultList.add(R.drawable.ic_baseline_watch)
        resultList.add(R.drawable.ic_baseline_vpn_key)
        return resultList
    }
}