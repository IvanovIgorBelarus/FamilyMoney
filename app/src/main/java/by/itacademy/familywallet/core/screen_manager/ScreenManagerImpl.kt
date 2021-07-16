package by.itacademy.familywallet.core.screen_manager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.itacademy.familywallet.core.screen_manager.ScreenManager

class ScreenManagerImpl(
    private val container: Int,
    private val activity: AppCompatActivity
) : ScreenManager {

    override fun startFragment(fragment: Fragment) {
            activity.supportFragmentManager
                .beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit()
    }
}