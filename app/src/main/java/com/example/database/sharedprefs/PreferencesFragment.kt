package com.example.database.sharedprefs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.database.databinding.FragmentPreferencesBinding

class PreferencesFragment : Fragment() {

    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val prefsManager by lazy {
        PrefsManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPreferencesBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            switchView.isChecked = prefsManager.switchIsChecked
            editText.setText(prefsManager.editTextValue)

            switchView.setOnCheckedChangeListener { _, isChecked ->
                prefsManager.switchIsChecked = isChecked
            }

            editText.addTextChangedListener { text ->
                prefsManager.editTextValue = text.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}