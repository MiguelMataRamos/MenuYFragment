package com.example.menu

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //funcionalidad boton cerrar fragmento
        val button = findPreference<Preference>("cerrar")
        button?.setOnPreferenceClickListener {
            //cerrar el fragmento
            this.activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            true
        }

    }
}