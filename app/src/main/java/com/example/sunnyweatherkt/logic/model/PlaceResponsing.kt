package com.example.sunnyweatherkt.logic.model

import com.google.gson.annotations.SerializedName

class PlaceResponsing {                                                                               //PlaceResponse bean

    //  https://api.caiyunapp.com/v2/place?query=munich&token=ggepE823AvATQ6dj&lang=zh

   /*
   {"status":"ok","query":"munich",
   "places":[{"id":"place.8366109811859780","location":{"lat":48.13333,"lng":11.56667},"place_id":"mb-place.8366109811859780","name":"\u6155\u5c3c\u9ed1, \u5df4\u4f10\u5229\u4e9a, \u5fb7\u56fd","formatted_address":"\u6155\u5c3c\u9ed1"},
   {"id":"poi.326417519825","location":{"lat":48.3543275,"lng":11.7886795},"place_id":"mb-poi.326417519825","name":"\u6155\u5c3c\u9ed1\u6a5f\u5834, Nordallee 25, \u5965\u4f2f\u4e01, \u5df4\u4f10\u5229\u4e9a 85356,\u5fb7\u56fd","formatted_address":"\u6155\u5c3c\u9ed1\u6a5f\u5834"},
   {"id":"poi.738734384869","location":{"lat":48.141543125,"lng":11.57879975},"place_id":"mb-poi.738734384869","name":"\u6155\u5c3c\u9ed1\u738b\u5bab, Residenzstr. 1, \u6155\u5c3c\u9ed1,\u5df4\u4f10\u5229\u4e9a 80539, \u5fb7\u56fd","formatted_address":"\u6155\u5c3c\u9ed1\u738b\u5bab"},
   {"id":"poi.472446458928","location":{"lat":48.148286,"lng":11.5699605},"place_id":"mb-poi.472446458928","name":"\u8001\u7ed8\u753b\u9648\u5217\u9986, Barer Str. 27, \u6155\u5c3c\u9ed1,\u5df4\u4f10\u5229\u4e9a 80333, \u5fb7\u56fd","formatted_address":"\u8001\u7ed8\u753b\u9648\u5217\u9986"},
   {"id":"poi.712964588975","location":{"lat":48.13647025,"lng":11.57626925},"place_id":"mb-poi.712964588975","name":"\u6155\u5c3c\u9ed1\u5723\u4f2f\u591a\u7984\u6559\u5802, Petersplatz, \u6155\u5c3c\u9ed1,\u5df4\u4f10\u5229\u4e9a 80331, \u5fb7\u56fd","formatted_address":"\u6155\u5c3c\u9ed1\u5723\u4f2f\u591a\u7984\u6559\u5802"}]}
*/

    //data class 即 java 中的 bean
    data class PlaceResponse(val status:String, val places:List<Place>)
    data class Place(val name:String, val location:Location,@SerializedName("formatted_address")val address:String)
    data class Location(val lng:String,val lat:String)
}