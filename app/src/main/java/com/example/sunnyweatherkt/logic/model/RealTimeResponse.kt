package com.example.sunnyweatherkt.logic.model

data class RealTimeResponse(val status:String,val result:Result){

    /*
    {"status":"ok","api_version":"v2.5","api_status":"active","lang":"zh_CN","unit":"metric","tzshift":28800,"timezone":"Asia\/Taipei","server_time":1616919380,"location":[25.1552,121.6544],
    "result":{
        "realtime":{"status":"ok","temperature":20.16,"humidity":0.71,"cloudrate":0.3,"skycon":"PARTLY_CLOUDY_DAY","visibility":8.9,"dswrf":545.8,
        "wind":{"speed":15.48,"direction":90.0},
        "pressure":99146.82,"apparent_temperature":18.8,
        "precipitation":{"local":{"status":"ok","datasource":"radar","intensity":0.0},"nearest":{"status":"ok","distance":5.0,"intensity":2.0}},
        "air_quality":{"pm25":14,"pm10":0,"o3":0,"so2":0,"no2":0,"co":0,"aqi":{"chn":21,"usa":0},"description":{"usa":"","chn":"\u4f18"}},
        "life_index":{"ultraviolet":{"index":3.0,"desc":"\u5f31"},"comfort":{"index":5,"desc":"\u8212\u9002"}}},"primary":0
            }
    }
     */

    data class Result(val realtime:RealTime)
    data class RealTime(val temperature:Float,val skycon:String,val humidity:String,val pressure:Float, val air_quality:AirQuality,val life_index:LifeIndex)
    data class AirQuality(val pm25:Float,val aqi:AQI)
    data class LifeIndex(val ultraviolet:UltraViolet,val comfort:Comfort)
    data class AQI(val chn:Float,val usa:Float)
    data class UltraViolet(val index:Float)
    data class Comfort(val index: Float)
}

