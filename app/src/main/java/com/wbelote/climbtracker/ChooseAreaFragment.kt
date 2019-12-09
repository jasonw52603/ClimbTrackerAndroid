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
class ChooseAreaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_area, container, false)

        val rv: RecyclerView = view.findViewById(R.id.rvAreas)
        rv.adapter = AreaAdapter()
        rv.layoutManager = LinearLayoutManager(view.context)

        return view
    }


    class AreaAdapter : RecyclerView.Adapter<AreaAdapter.AreaViewHolder>() {

        class AreaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val tv: TextView = view.findViewById(R.id.tvGymOption)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gym, parent, false)
            return AreaViewHolder(view)
        }

        override fun getItemCount(): Int = GymInfo.areaCount

        override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
            holder.tv.text = GymInfo.areaNames[position]

            holder.view.setOnClickListener {

                GymInfo.chooseArea(position)

                val activity = ChooseAreaFragmentDirections
                    .actionChooseAreaFragmentToChooseProblemFragment()
                holder.view.findNavController().navigate(activity)

            }
        }

    }

}
