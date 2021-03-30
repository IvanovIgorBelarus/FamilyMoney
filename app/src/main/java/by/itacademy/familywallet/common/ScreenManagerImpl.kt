package by.itacademy.familywallet.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ScreenManagerImpl(
    private val container: Int,
    private val activity: AppCompatActivity
):ScreenManager {
    override fun startFragment(fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .add(container, fragment)
            .addToBackStack(null)
            .commit()
    }
}