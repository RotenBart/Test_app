package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.SpecialistsDbHelper
import com.example.testapp.entity.Specialty
import com.example.testapp.recycler.SpecialtyRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_specialties.*

class SpecialtiesListFragment : Fragment() {

    private var specialtyList = arrayListOf<Specialty>()
    private var specialtyAdapter = SpecialtyRecyclerAdapter()

//    companion object {
//        const val SPECIALTY_LIST = "SPECIALTY_LIST"
//        fun newInstance(specialtyList: ArrayList<Specialty>): SpecialtiesListFragment {
//            val bundle = Bundle()
//            val fragment = SpecialtiesListFragment()
//            bundle.putParcelableArrayList(SPECIALTY_LIST, specialtyList)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_specialties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if(arguments?.getSerializable(SPECIALTY_LIST) != null) {
//            specialtyList = arguments?.getParcelableArrayList<Specialty>(SPECIALTY_LIST) as ArrayList<Specialty>
//        }
        val dbHelper = SpecialistsDbHelper(requireContext())
        val specialtyList = dbHelper.getAllSpecialties()

        recyclerView.apply {
            adapter = specialtyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        specialtyAdapter.setItems(specialtyList)
        specialtyAdapter.specialtyClick = {
            showSpecialistListFragment(it)
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSpecialistListFragment(id: Int){
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, SpecialistListFragment.newInstance(id))?.addToBackStack(SpecialistListFragment::javaClass.name)?.commit()
    }
}