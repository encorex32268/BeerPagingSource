package com.example.beerpagingsource.domain.util

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AppendErrorDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Append Error")
            .setPositiveButton("Ok"){ _,_->
                this.dismiss()
            }

        return builder.create()
    }
}