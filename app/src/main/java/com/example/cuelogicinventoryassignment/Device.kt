package com.example.cuelogicinventoryassignment

class Device  (var deviceKey: String, val deviceName: String, val platform: String, val OS: String, val date: String){
    constructor() : this("","","","",""){
    }

    constructor(toString: String, toString1: String, toString2: String, toString3: String) : this()
}
