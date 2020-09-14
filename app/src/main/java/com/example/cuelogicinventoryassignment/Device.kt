package com.example.cuelogicinventoryassignment

class Device  (var deviceKey: String, val deviceName: String, val platform: String, val OS: String, val date: String,val AssignedTo: String?, val dateAssgned: String?){
    constructor() : this("","","","","","",""){
    }

    constructor(toString: String, toString1: String, toString2: String, toString3: String) : this()
    constructor(toString: String, toString1: String, toString2: String, toString3: String, toString4: String, date: String) : this()
}
