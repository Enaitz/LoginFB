package com.example.loginfb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(private val userList:ArrayList<User>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rvusers,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsersAdapter.MyViewHolder, position: Int) {

        val user: User = userList[position]
        holder.nom.text = user.nom
        holder.mail.text = user.mail
        holder.data_naixement.text = user.data_naixement
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nom: TextView = itemView.findViewById(R.id.txtNom)
        val mail: TextView = itemView.findViewById(R.id.txtMail)
        val data_naixement: TextView = itemView.findViewById(R.id.txtNaixement)
    }
}