package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.testapp.SpecialistListFragment.Companion.ID
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialist
import kotlinx.android.synthetic.main.fragment_specialist_details.*

class SpecialistDetailsFragment : Fragment() {

    companion object {
        fun newInstance(specialistId: Int): SpecialistDetailsFragment {
            val bundle = Bundle()
            val fragment = SpecialistDetailsFragment()
            bundle.putInt(ID, specialistId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specialist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: Int? = null
        if (arguments != null) {
            id = arguments?.getInt(ID, 0)
        }
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialist = dbHelper.getSpecialistById(id)
        setSpecialistInfo(specialist)
    }

    private fun setSpecialistInfo(specialist: Specialist) {
        specialistAvatar.load(specialist.avatarUrl)
        specialistFirstName.text = specialist.firstName
        specialistLastName.text = specialist.lastName
        if (specialist.birthday != null) {
            specialistBirthdayTextView.text = specialist.birthday
            specialistAgeTextView.text = specialist.getSpecialistAge()
        } else {
            specialistBirthdayTextView.text = "-"
            specialistAgeTextView.text = "-"
        }
        specialtyTextView.text = specialist.specialty?.get(0)?.specialtyName
    }
}