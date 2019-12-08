package com.wbelote.climbtracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class ChooseGymFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_gym, container, false)

        val rv: RecyclerView = view.findViewById(R.id.rvGyms)
        rv.adapter = GymAdapter()
        rv.layoutManager = LinearLayoutManager(view!!.context)

        return view
    }


    class GymAdapter :
        RecyclerView.Adapter<GymAdapter.GymViewHolder>() {

        class GymViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val tv: TextView = view.findViewById(R.id.tvGymOption)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gym, parent, false)
            return GymViewHolder(view)
        }

        override fun getItemCount(): Int = GymInfo.gymCount

        override fun onBindViewHolder(holder: GymViewHolder, position: Int) {

            holder.tv.text = GymInfo.gymNames[position]

            holder.view.setOnClickListener {
                GymInfo.chooseGym(position)
                val action = ChooseGymFragmentDirections
                    .actionChooseGymFragmentToChooseAreaFragment()
                holder.view.findNavController().navigate(action)
            }

        }

    }

}
