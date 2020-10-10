package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.recycler.SpecialtyRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list_specialties.*

class SpecialistListFragment: Fragment() {

    private var specialistAdapter = SpecialtyRecyclerAdapter()

    companion object {
        const val ID = "ID"
        fun newInstance(specialistId: Int): SpecialistListFragment {
            val bundle = Bundle()
            val fragment = SpecialistListFragment()
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
        return inflater.inflate(R.layout.fragment_list_specialties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var id: Int? = null
        if(arguments != null) {
            id = arguments?.getInt("ID",0)
        }
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialistList = dbHelper.getAllSpecialistsBySpecialtyId(id)

        recyclerView.apply {
            adapter = specialistAdapter
            layoutManager = LinearLayoutManager(context)
        }
        specialistAdapter.setItems(specialistList)
        specialistAdapter.specialtyClick = {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}