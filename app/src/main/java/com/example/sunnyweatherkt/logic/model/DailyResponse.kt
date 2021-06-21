package com.example.sunnyweatherkt.logic.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class DailyResponse(val status:String,val result:Result){

    data class Result(val daily:Daily)
    data class Daily(val temperature:List<Temperature>,val skycon:List<Skycon>,val life_index:LifeIndex)
    data class Temperature(val max:Float,val min:Float)
    data class Skycon(val value:String,val date:Date)
    data class LifeIndex(val coldRisk:List<Description>,val carWashing:List<Description>,val ultraviolet:List<Description>,val dressing:List<Description>)
    data class Description(val desc:String)


}

/*
{"status":"ok","api_version":"v2.5","api_status":"active","lang":"zh_CN","unit":"metric","tzshift":28800,"timezone":"Asia\/Taipei",
"server_time":1617474889,"location":[25.1552,121.6544],

"result":{"daily":
    {"status":"ok","astro":[{"date":"2021-04-04T00:00+08:00","sunrise":{"time":"05:41"},
"sunset":{"time":"18:11"}},{"date":"2021-04-05T00:00+08:00","sunrise":{"time":"05:40"},"sunset":{"time":"18:11"}},{"date":"2021-04-06T00:00+08:00",
"sunrise":{"time":"05:39"},"sunset":{"time":"18:11"}},{"date":"2021-04-07T00:00+08:00","sunrise":{"time":"05:38"},"sunset":{"time":"18:12"}},
{"date":"2021-04-08T00:00+08:00","sunrise":{"time":"05:37"},"sunset":{"time":"18:12"}}],

"precipitation":[{"date":"2021-04-04T00:00+08:00",
"max":0.4421,"min":0.0,"avg":0.0832},{"date":"2021-04-05T00:00+08:00","max":0.0,"min":0.0,"avg":0.0},{"date":"2021-04-06T00:00+08:00",
"max":0.0,"min":0.0,"avg":0.0},{"date":"2021-04-07T00:00+08:00","max":0.0,"min":0.0,"avg":0.0},{"date":"2021-04-08T00:00+08:00","max":0.3173,"min":0.0,"avg":0.0756}],

"temperature":[{"date":"2021-04-04T00:00+08:00","max":18.5,"min":15.0,"avg":16.25},{"date":"2021-04-05T00:00+08:00","max":19.0,"min":15.0,"avg":16.88},
{"date":"2021-04-06T00:00+08:00","max":22.0,"min":18.0,"avg":19.66},{"date":"2021-04-07T00:00+08:00","max":21.0,"min":18.0,"avg":19.17},
{"date":"2021-04-08T00:00+08:00","max":21.0,"min":18.0,"avg":18.88}],

"wind":[{"date":"2021-04-04T00:00+08:00","max":{"speed":27.18,"direction":42.26},
"min":{"speed":17.32,"direction":40.61},"avg":{"speed":23.73,"direction":45.5}},{"date":"2021-04-05T00:00+08:00","max":{"speed":22.62,"direction":30.17},
"min":{"speed":13.71,"direction":54.16},"avg":{"speed":19.49,"direction":37.46}},{"date":"2021-04-06T00:00+08:00","max":{"speed":22.29,"direction":42.96},
"min":{"speed":12.69,"direction":54.81},"avg":{"speed":16.91,"direction":52.79}},{"date":"2021-04-07T00:00+08:00","max":{"speed":23.43,"direction":41.03},
"min":{"speed":15.08,"direction":59.39},"avg":{"speed":19.66,"direction":53.79}},{"date":"2021-04-08T00:00+08:00","max":{"speed":25.55,"direction":27.53},
"min":{"speed":17.73,"direction":45.92},"avg":{"speed":22.38,"direction":35.95}}],

"humidity":[{"date":"2021-04-04T00:00+08:00","max":0.93,"min":0.69,"avg":0.85},
{"date":"2021-04-05T00:00+08:00","max":0.73,"min":0.53,"avg":0.62},{"date":"2021-04-06T00:00+08:00","max":0.76,"min":0.61,"avg":0.69},
{"date":"2021-04-07T00:00+08:00","max":0.74,"min":0.62,"avg":0.68},{"date":"2021-04-08T00:00+08:00","max":0.82,"min":0.67,"avg":0.73}],

"cloudrate":[{"date":"2021-04-04T00:00+08:00","max":1.0,"min":0.3,"avg":0.9},{"date":"2021-04-05T00:00+08:00","max":0.61,"min":0.0,"avg":0.09},
{"date":"2021-04-06T00:00+08:00","max":0.22,"min":0.0,"avg":0.06},{"date":"2021-04-07T00:00+08:00","max":1.0,"min":0.0,"avg":0.41},
{"date":"2021-04-08T00:00+08:00","max":1.0,"min":0.97,"avg":1.0}],

"pressure":[{"date":"2021-04-04T00:00+08:00","max":100427.16,"min":99737.49,"avg":100097.0},
{"date":"2021-04-05T00:00+08:00","max":100438.76,"min":100187.16,"avg":100314.29},{"date":"2021-04-06T00:00+08:00","max":100328.16,"min":100107.16,"avg":100202.17},
{"date":"2021-04-07T00:00+08:00","max":100248.16,"min":99935.55,"avg":100095.61},{"date":"2021-04-08T00:00+08:00","max":100267.16,"min":99965.89,"avg":100109.0}],

"visibility":[{"date":"2021-04-04T00:00+08:00","max":19.4,"min":5.9,"avg":8.79},{"date":"2021-04-05T00:00+08:00","max":19.4,"min":14.82,"avg":19.05},
{"date":"2021-04-06T00:00+08:00","max":19.4,"min":12.13,"avg":17.03},{"date":"2021-04-07T00:00+08:00","max":19.4,"min":14.11,"avg":18.45},
{"date":"2021-04-08T00:00+08:00","max":19.4,"min":8.79,"avg":15.57}],

"dswrf":[{"date":"2021-04-04T00:00+08:00","max":149.3,"min":0.0,"avg":52.3},
{"date":"2021-04-05T00:00+08:00","max":864.2,"min":0.0,"avg":324.3},{"date":"2021-04-06T00:00+08:00","max":856.8,"min":0.0,"avg":321.0},
{"date":"2021-04-07T00:00+08:00","max":830.5,"min":0.0,"avg":297.1},{"date":"2021-04-08T00:00+08:00","max":142.0,"min":0.0,"avg":35.0}],
"air_quality":{"aqi":[{"date":"2021-04-04T00:00+08:00","max":{"chn":13,"usa":13},

"avg":{"chn":9.91,"usa":9.91},"min":{"chn":9,"usa":9}},
{"date":"2021-04-05T00:00+08:00","max":{"chn":14,"usa":14},"avg":{"chn":11.21,"usa":11.21},"min":{"chn":10,"usa":10}},
{"date":"2021-04-06T00:00+08:00","max":{"chn":16,"usa":16},"avg":{"chn":13.54,"usa":13.54},"min":{"chn":12,"usa":12}},
{"date":"2021-04-07T00:00+08:00","max":{"chn":17,"usa":17},"avg":{"chn":13.88,"usa":13.88},"min":{"chn":12,"usa":12}},
{"date":"2021-04-08T00:00+08:00","max":{"chn":12,"usa":12},"avg":{"chn":9.79,"usa":9.79},"min":{"chn":8,"usa":8}}],

"pm25":[{"date":"2021-04-04T00:00+08:00","max":10,"avg":7.18,"min":6},
{"date":"2021-04-05T00:00+08:00","max":10,"avg":8.54,"min":7},
{"date":"2021-04-06T00:00+08:00","max":11,"avg":9.88,"min":9},
{"date":"2021-04-07T00:00+08:00","max":12,"avg":10.0,"min":8},
{"date":"2021-04-08T00:00+08:00","max":8,"avg":7.17,"min":6}]},

"skycon":[{"date":"2021-04-04T00:00+08:00","value":"LIGHT_RAIN"},
{"date":"2021-04-05T00:00+08:00","value":"CLEAR_DAY"},
{"date":"2021-04-06T00:00+08:00","value":"CLEAR_DAY"},
{"date":"2021-04-07T00:00+08:00","value":"PARTLY_CLOUDY_DAY"},
{"date":"2021-04-08T00:00+08:00","value":"LIGHT_RAIN"}],

"skycon_08h_20h":[{"date":"2021-04-04T00:00+08:00","value":"LIGHT_RAIN"},
{"date":"2021-04-05T00:00+08:00","value":"CLEAR_DAY"},
{"date":"2021-04-06T00:00+08:00","value":"CLEAR_DAY"},
{"date":"2021-04-07T00:00+08:00","value":"PARTLY_CLOUDY_DAY"},
{"date":"2021-04-08T00:00+08:00","value":"LIGHT_RAIN"}],

"skycon_20h_32h":[{"date":"2021-04-04T00:00+08:00","value":"PARTLY_CLOUDY_NIGHT"},
{"date":"2021-04-05T00:00+08:00","value":"CLEAR_NIGHT"},
{"date":"2021-04-06T00:00+08:00","value":"CLEAR_NIGHT"},
{"date":"2021-04-07T00:00+08:00","value":"CLOUDY"},
{"date":"2021-04-08T00:00+08:00","value":"CLOUDY"}],

"life_index":{"ultraviolet":[{"date":"2021-04-04T00:00+08:00","index":"1","desc":"\u6700\u5f31"},
{"date":"2021-04-05T00:00+08:00","index":"5","desc":"\u5f88\u5f3a"},
{"date":"2021-04-06T00:00+08:00","index":"5","desc":"\u5f88\u5f3a"},
{"date":"2021-04-07T00:00+08:00","index":"4","desc":"\u5f3a"},
{"date":"2021-04-08T00:00+08:00","index":"1","desc":"\u6700\u5f31"}],

"carWashing":[{"date":"2021-04-04T00:00+08:00","index":"3","desc":"\u8f83\u4e0d\u9002\u5b9c"},
{"date":"2021-04-05T00:00+08:00","index":"1","desc":"\u9002\u5b9c"},{"date":"2021-04-06T00:00+08:00","index":"1","desc":"\u9002\u5b9c"},
{"date":"2021-04-07T00:00+08:00","index":"3","desc":"\u8f83\u4e0d\u9002\u5b9c"},
{"date":"2021-04-08T00:00+08:00","index":"3","desc":"\u8f83\u4e0d\u9002\u5b9c"}],

"dressing":[{"date":"2021-04-04T00:00+08:00","index":"5","desc":"\u51c9\u723d"},
{"date":"2021-04-05T00:00+08:00","index":"5","desc":"\u51c9\u723d"},
{"date":"2021-04-06T00:00+08:00","index":"5","desc":"\u51c9\u723d"},
{"date":"2021-04-07T00:00+08:00","index":"5","desc":"\u51c9\u723d"},
{"date":"2021-04-08T00:00+08:00","index":"5","desc":"\u51c9\u723d"}],

"comfort":[{"date":"2021-04-04T00:00+08:00","index":"7","desc":"\u51b7"},
{"date":"2021-04-05T00:00+08:00","index":"7","desc":"\u51b7"},
{"date":"2021-04-06T00:00+08:00","index":"5","desc":"\u8212\u9002"},
{"date":"2021-04-07T00:00+08:00","index":"5","desc":"\u8212\u9002"},
{"date":"2021-04-08T00:00+08:00","index":"5","desc":"\u8212\u9002"}],

"coldRisk":[{"date":"2021-04-04T00:00+08:00","index":"3","desc":"\u6613\u53d1"},
{"date":"2021-04-05T00:00+08:00","index":"3","desc":"\u6613\u53d1"},
{"date":"2021-04-06T00:00+08:00","index":"3","desc":"\u6613\u53d1"},
{"date":"2021-04-07T00:00+08:00","index":"3","desc":"\u6613\u53d1"},
{"date":"2021-04-08T00:00+08:00","index":"3","desc":"\u6613\u53d1"}]}},"primary":0}}

 */


