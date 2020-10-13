package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialty
import com.example.testapp.recycler.SpecialtyRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list_specialties.*

class SpecialtiesListFragment : Fragment(R.layout.fragment_list_specialties) {

    private var specialtyAdapter = SpecialtyRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialtyList = dbHelper.getAllSpecialties()
        setSpecialtyRecyclerView(specialtyList)
        setActionBar()
    }

    private fun showSpecialistListFragment(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SpecialistListFragment.newInstance(id))
            .addToBackStack(SpecialistListFragment::javaClass.name)
            .commit()
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
            val dividerItemDecoration = DividerItemDecoration(context,RecyclerView.VERTICAL)
            AppCompatResources.getDrawable(context, R.drawable.divider_drawable)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            addItemDecoration(dividerItemDecoration)
        }
        specialtyAdapter.setItems(specialtyList)
        specialtyAdapter.specialtyClick = {
            showSpecialistListFragment(it)
        }
    }
}