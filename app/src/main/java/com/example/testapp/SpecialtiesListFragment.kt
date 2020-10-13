package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialty
import com.example.testapp.recycler.SpecialtyRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list_specialties.*

class SpecialtiesListFragment : Fragment() {

    private var specialtyAdapter = SpecialtyRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_specialties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialtyList = dbHelper.getAllSpecialties()
        setSpecialtyRecyclerView(specialtyList)
        setActionBar()
    }

    private fun showSpecialistListFragment(id: Int) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, SpecialistListFragment.newInstance(id))
            ?.addToBackStack(SpecialistListFragment::javaClass.name)?.commit()
    }

    private fun setActionBar() {
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Список специальностей"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setSpecialtyRecyclerView(specialtyList: ArrayList<Specialty>) {
        recyclerView.apply {
            adapter = specialtyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        specialtyAdapter.setItems(specialtyList)
        specialtyAdapter.specialtyClick = {
            showSpecialistListFragment(it)
        }
    }
}