package com.jayneel.socialmedia.Model

class MsgModel(var msgid:String?=null,var sender:String?=null,var reciver:String?=null,var isseen:Boolean?=null,var msg:String?=null) {
    constructor():this("","","",false,"")
}