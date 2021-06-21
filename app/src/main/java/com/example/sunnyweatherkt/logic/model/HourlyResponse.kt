package com.example.sunnyweatherkt.logic.model

import java.util.*

class HourlyResponse(val status:String,val result:Result,val timezone:String,val tzshift:Int) {

    data class Result(val hourly:Hourly)
    data class Hourly(val temperature:List<Temperature>, val skycon:List<Skycon>,val description:String)
    data class Temperature(val dateTime:Date,val value:Float)
    data class Skycon(val datetime:Date,val value:String)



/*
    {"status":"ok","api_version":"v2.5","api_status":"active","lang":"zh_CN","unit":"metric","tzshift":28800,"timezone":"Asia\/Taipei","server_time":1622879868,
        "location":[25.1552,121.6544],
        "result":{"hourly":{"status":"ok","description":"\u5c0f\u96e8\uff0c\u4eca\u5929\u665a\u95f420\u70b9\u949f\u540e\u96e8\u505c\uff0c\u8f6c\u9634\uff0c\u5176\u540e\u4e2d\u96e8",
        "precipitation":[{"datetime":"2021-06-05T15:00+08:00","value":0.4006},{"datetime":"2021-06-05T16:00+08:00","value":1.1564},{"datetime":"2021-06-05T17:00+08:00","value":2.1384},{"datetime":"2021-06-05T18:00+08:00","value":0.2155},{"datetime":"2021-06-05T19:00+08:00","value":0.4976},{"datetime":"2021-06-05T20:00+08:00","value":0.0561},{"datetime":"2021-06-05T21:00+08:00","value":17.5941},{"datetime":"2021-06-05T22:00+08:00","value":6.1489},{"datetime":"2021-06-05T23:00+08:00","value":1.8737},{"datetime":"2021-06-06T00:00+08:00","value":2.2781},{"datetime":"2021-06-06T01:00+08:00","value":8.2565},{"datetime":"2021-06-06T02:00+08:00","value":1.8107},{"datetime":"2021-06-06T03:00+08:00","value":0.8643},{"datetime":"2021-06-06T04:00+08:00","value":0.3122},{"datetime":"2021-06-06T05:00+08:00","value":0.086},{"datetime":"2021-06-06T06:00+08:00","value":0.3614},{"datetime":"2021-06-06T07:00+08:00","value":1.9837},{"datetime":"2021-06-06T08:00+08:00","value":3.366},{"datetime":"2021-06-06T09:00+08:00","value":0.1709},{"datetime":"2021-06-06T10:00+08:00","value":0.0},{"datetime":"2021-06-06T11:00+08:00","value":0.0},{"datetime":"2021-06-06T12:00+08:00","value":0.0},{"datetime":"2021-06-06T13:00+08:00","value":0.0},{"datetime":"2021-06-06T14:00+08:00","value":0.0},{"datetime":"2021-06-06T15:00+08:00","value":0.2575},{"datetime":"2021-06-06T16:00+08:00","value":0.2249},{"datetime":"2021-06-06T17:00+08:00","value":0.6342},{"datetime":"2021-06-06T18:00+08:00","value":0.1677},{"datetime":"2021-06-06T19:00+08:00","value":0.3369},{"datetime":"2021-06-06T20:00+08:00","value":3.0865},{"datetime":"2021-06-06T21:00+08:00","value":0.4835},{"datetime":"2021-06-06T22:00+08:00","value":0.0455},{"datetime":"2021-06-06T23:00+08:00","value":0.0},{"datetime":"2021-06-07T00:00+08:00","value":0.0634},{"datetime":"2021-06-07T01:00+08:00","value":0.0},{"datetime":"2021-06-07T02:00+08:00","value":0.0},{"datetime":"2021-06-07T03:00+08:00","value":0.0},{"datetime":"2021-06-07T04:00+08:00","value":0.0},{"datetime":"2021-06-07T05:00+08:00","value":0.0},{"datetime":"2021-06-07T06:00+08:00","value":0.0},{"datetime":"2021-06-07T07:00+08:00","value":0.0},{"datetime":"2021-06-07T08:00+08:00","value":0.0},{"datetime":"2021-06-07T09:00+08:00","value":0.0},{"datetime":"2021-06-07T10:00+08:00","value":0.0646},{"datetime":"2021-06-07T11:00+08:00","value":0.3145},{"datetime":"2021-06-07T12:00+08:00","value":0.1338},{"datetime":"2021-06-07T13:00+08:00","value":0.0485},{"datetime":"2021-06-07T14:00+08:00","value":0.0}],
        "temperature":[{"datetime":"2021-06-05T15:00+08:00","value":20.53},{"datetime":"2021-06-05T16:00+08:00","value":23.0},{"datetime":"2021-06-05T17:00+08:00","value":23.0},{"datetime":"2021-06-05T18:00+08:00","value":23.0},{"datetime":"2021-06-05T19:00+08:00","value":23.0},{"datetime":"2021-06-05T20:00+08:00","value":23.0},{"datetime":"2021-06-05T21:00+08:00","value":23.0},{"datetime":"2021-06-05T22:00+08:00","value":24.0},{"datetime":"2021-06-05T23:00+08:00","value":24.0},{"datetime":"2021-06-06T00:00+08:00","value":23.0},{"datetime":"2021-06-06T01:00+08:00","value":23.0},{"datetime":"2021-06-06T02:00+08:00","value":23.0},{"datetime":"2021-06-06T03:00+08:00","value":23.0},{"datetime":"2021-06-06T04:00+08:00","value":23.0},{"datetime":"2021-06-06T05:00+08:00","value":23.4},{"datetime":"2021-06-06T06:00+08:00","value":23.8},{"datetime":"2021-06-06T07:00+08:00","value":24.2},{"datetime":"2021-06-06T08:00+08:00","value":24.6},{"datetime":"2021-06-06T09:00+08:00","value":25.0},{"datetime":"2021-06-06T10:00+08:00","value":25.4},{"datetime":"2021-06-06T11:00+08:00","value":25.8},{"datetime":"2021-06-06T12:00+08:00","value":25.9},{"datetime":"2021-06-06T13:00+08:00","value":25.6},{"datetime":"2021-06-06T14:00+08:00","value":27.0},{"datetime":"2021-06-06T15:00+08:00","value":26.33},{"datetime":"2021-06-06T16:00+08:00","value":25.94},{"datetime":"2021-06-06T17:00+08:00","value":23.6},{"datetime":"2021-06-06T18:00+08:00","value":25.19},{"datetime":"2021-06-06T19:00+08:00","value":24.87},{"datetime":"2021-06-06T20:00+08:00","value":24.82},{"datetime":"2021-06-06T21:00+08:00","value":24.78},{"datetime":"2021-06-06T22:00+08:00","value":24.8},{"datetime":"2021-06-06T23:00+08:00","value":24.87},{"datetime":"2021-06-07T00:00+08:00","value":25.0},{"datetime":"2021-06-07T01:00+08:00","value":25.0},{"datetime":"2021-06-07T02:00+08:00","value":25.0},{"datetime":"2021-06-07T03:00+08:00","value":25.0},{"datetime":"2021-06-07T04:00+08:00","value":25.0},{"datetime":"2021-06-07T05:00+08:00","value":25.0},{"datetime":"2021-06-07T06:00+08:00","value":25.0},{"datetime":"2021-06-07T07:00+08:00","value":25.28},{"datetime":"2021-06-07T08:00+08:00","value":25.69},{"datetime":"2021-06-07T09:00+08:00","value":26.06},{"datetime":"2021-06-07T10:00+08:00","value":26.5},{"datetime":"2021-06-07T11:00+08:00","value":26.85},{"datetime":"2021-06-07T12:00+08:00","value":27.26},{"datetime":"2021-06-07T13:00+08:00","value":27.52},{"datetime":"2021-06-07T14:00+08:00","value":27.78}],
        "wind":[{"datetime":"2021-06-05T15:00+08:00","speed":24.12,"direction":0.0},{"datetime":"2021-06-05T16:00+08:00","speed":16.61,"direction":46.68},{"datetime":"2021-06-05T17:00+08:00","speed":14.21,"direction":59.35},{"datetime":"2021-06-05T18:00+08:00","speed":13.67,"direction":63.68},{"datetime":"2021-06-05T19:00+08:00","speed":13.6,"direction":66.59},{"datetime":"2021-06-05T20:00+08:00","speed":13.33,"direction":75.07},{"datetime":"2021-06-05T21:00+08:00","speed":14.21,"direction":52.9},{"datetime":"2021-06-05T22:00+08:00","speed":17.58,"direction":40.72},{"datetime":"2021-06-05T23:00+08:00","speed":17.61,"direction":40.43},{"datetime":"2021-06-06T00:00+08:00","speed":18.31,"direction":44.38},{"datetime":"2021-06-06T01:00+08:00","speed":19.12,"direction":48.22},{"datetime":"2021-06-06T02:00+08:00","speed":17.76,"direction":48.91},{"datetime":"2021-06-06T03:00+08:00","speed":16.43,"direction":44.12},{"datetime":"2021-06-06T04:00+08:00","speed":13.92,"direction":39.25},{"datetime":"2021-06-06T05:00+08:00","speed":16.35,"direction":37.74},{"datetime":"2021-06-06T06:00+08:00","speed":14.15,"direction":34.03},{"datetime":"2021-06-06T07:00+08:00","speed":13.86,"direction":28.11},{"datetime":"2021-06-06T08:00+08:00","speed":12.93,"direction":20.26},{"datetime":"2021-06-06T09:00+08:00","speed":12.67,"direction":27.84},{"datetime":"2021-06-06T10:00+08:00","speed":15.07,"direction":12.4},{"datetime":"2021-06-06T11:00+08:00","speed":16.55,"direction":13.72},{"datetime":"2021-06-06T12:00+08:00","speed":15.65,"direction":14.77},{"datetime":"2021-06-06T13:00+08:00","speed":14.46,"direction":26.2},{"datetime":"2021-06-06T14:00+08:00","speed":12.54,"direction":37.14},{"datetime":"2021-06-06T15:00+08:00","speed":13.78,"direction":33.16},{"datetime":"2021-06-06T16:00+08:00","speed":13.07,"direction":36.59},{"datetime":"2021-06-06T17:00+08:00","speed":12.46,"direction":50.45},{"datetime":"2021-06-06T18:00+08:00","speed":11.57,"direction":64.27},{"datetime":"2021-06-06T19:00+08:00","speed":10.36,"direction":65.34},{"datetime":"2021-06-06T20:00+08:00","speed":12.05,"direction":88.16},{"datetime":"2021-06-06T21:00+08:00","speed":12.17,"direction":83.37},{"datetime":"2021-06-06T22:00+08:00","speed":11.95,"direction":87.62},{"datetime":"2021-06-06T23:00+08:00","speed":11.82,"direction":94.55},{"datetime":"2021-06-07T00:00+08:00","speed":10.89,"direction":84.4},{"datetime":"2021-06-07T01:00+08:00","speed":9.48,"direction":80.41},{"datetime":"2021-06-07T02:00+08:00","speed":9.77,"direction":57.56},{"datetime":"2021-06-07T03:00+08:00","speed":10.63,"direction":47.13},{"datetime":"2021-06-07T04:00+08:00","speed":11.89,"direction":44.59},{"datetime":"2021-06-07T05:00+08:00","speed":13.35,"direction":51.89},{"datetime":"2021-06-07T06:00+08:00","speed":12.13,"direction":55.21},{"datetime":"2021-06-07T07:00+08:00","speed":13.66,"direction":57.23},{"datetime":"2021-06-07T08:00+08:00","speed":14.48,"direction":57.64},{"datetime":"2021-06-07T09:00+08:00","speed":15.09,"direction":61.05},{"datetime":"2021-06-07T10:00+08:00","speed":14.31,"direction":58.66},{"datetime":"2021-06-07T11:00+08:00","speed":15.32,"direction":52.6},{"datetime":"2021-06-07T12:00+08:00","speed":14.55,"direction":50.46},{"datetime":"2021-06-07T13:00+08:00","speed":13.36,"direction":40.49},{"datetime":"2021-06-07T14:00+08:00","speed":12.29,"direction":35.18}],
        "humidity":[{"datetime":"2021-06-05T15:00+08:00","value":0.95},{"datetime":"2021-06-05T16:00+08:00","value":0.92},{"datetime":"2021-06-05T17:00+08:00","value":0.92},{"datetime":"2021-06-05T18:00+08:00","value":0.91},{"datetime":"2021-06-05T19:00+08:00","value":0.91},{"datetime":"2021-06-05T20:00+08:00","value":0.92},{"datetime":"2021-06-05T21:00+08:00","value":0.94},{"datetime":"2021-06-05T22:00+08:00","value":0.94},{"datetime":"2021-06-05T23:00+08:00","value":0.94},{"datetime":"2021-06-06T00:00+08:00","value":0.94},{"datetime":"2021-06-06T01:00+08:00","value":0.94},{"datetime":"2021-06-06T02:00+08:00","value":0.94},{"datetime":"2021-06-06T03:00+08:00","value":0.94},{"datetime":"2021-06-06T04:00+08:00","value":0.95},{"datetime":"2021-06-06T05:00+08:00","value":0.95},{"datetime":"2021-06-06T06:00+08:00","value":0.94},{"datetime":"2021-06-06T07:00+08:00","value":0.93},{"datetime":"2021-06-06T08:00+08:00","value":0.93},{"datetime":"2021-06-06T09:00+08:00","value":0.93},{"datetime":"2021-06-06T10:00+08:00","value":0.93},{"datetime":"2021-06-06T11:00+08:00","value":0.91},{"datetime":"2021-06-06T12:00+08:00","value":0.89},{"datetime":"2021-06-06T13:00+08:00","value":0.87},{"datetime":"2021-06-06T14:00+08:00","value":0.86},{"datetime":"2021-06-06T15:00+08:00","value":0.88},{"datetime":"2021-06-06T16:00+08:00","value":0.9},{"datetime":"2021-06-06T17:00+08:00","value":0.9},{"datetime":"2021-06-06T18:00+08:00","value":0.9},{"datetime":"2021-06-06T19:00+08:00","value":0.9},{"datetime":"2021-06-06T20:00+08:00","value":0.9},{"datetime":"2021-06-06T21:00+08:00","value":0.9},{"datetime":"2021-06-06T22:00+08:00","value":0.89},{"datetime":"2021-06-06T23:00+08:00","value":0.9},{"datetime":"2021-06-07T00:00+08:00","value":0.89},{"datetime":"2021-06-07T01:00+08:00","value":0.88},{"datetime":"2021-06-07T02:00+08:00","value":0.88},{"datetime":"2021-06-07T03:00+08:00","value":0.89},{"datetime":"2021-06-07T04:00+08:00","value":0.89},{"datetime":"2021-06-07T05:00+08:00","value":0.89},{"datetime":"2021-06-07T06:00+08:00","value":0.89},{"datetime":"2021-06-07T07:00+08:00","value":0.88},{"datetime":"2021-06-07T08:00+08:00","value":0.88},{"datetime":"2021-06-07T09:00+08:00","value":0.87},{"datetime":"2021-06-07T10:00+08:00","value":0.86},{"datetime":"2021-06-07T11:00+08:00","value":0.86},{"datetime":"2021-06-07T12:00+08:00","value":0.85},{"datetime":"2021-06-07T13:00+08:00","value":0.85},{"datetime":"2021-06-07T14:00+08:00","value":0.84}],
        "cloudrate":[{"datetime":"2021-06-05T15:00+08:00","value":1.0},{"datetime":"2021-06-05T16:00+08:00","value":1.0},{"datetime":"2021-06-05T17:00+08:00","value":1.0},{"datetime":"2021-06-05T18:00+08:00","value":1.0},{"datetime":"2021-06-05T19:00+08:00","value":1.0},{"datetime":"2021-06-05T20:00+08:00","value":1.0},{"datetime":"2021-06-05T21:00+08:00","value":1.0},{"datetime":"2021-06-05T22:00+08:00","value":1.0},{"datetime":"2021-06-05T23:00+08:00","value":1.0},{"datetime":"2021-06-06T00:00+08:00","value":1.0},{"datetime":"2021-06-06T01:00+08:00","value":1.0},{"datetime":"2021-06-06T02:00+08:00","value":1.0},{"datetime":"2021-06-06T03:00+08:00","value":1.0},{"datetime":"2021-06-06T04:00+08:00","value":1.0},{"datetime":"2021-06-06T05:00+08:00","value":1.0},{"datetime":"2021-06-06T06:00+08:00","value":1.0},{"datetime":"2021-06-06T07:00+08:00","value":1.0},{"datetime":"2021-06-06T08:00+08:00","value":1.0},{"datetime":"2021-06-06T09:00+08:00","value":1.0},{"datetime":"2021-06-06T10:00+08:00","value":1.0},{"datetime":"2021-06-06T11:00+08:00","value":1.0},{"datetime":"2021-06-06T12:00+08:00","value":1.0},{"datetime":"2021-06-06T13:00+08:00","value":1.0},{"datetime":"2021-06-06T14:00+08:00","value":1.0},{"datetime":"2021-06-06T15:00+08:00","value":1.0},{"datetime":"2021-06-06T16:00+08:00","value":1.0},{"datetime":"2021-06-06T17:00+08:00","value":1.0},{"datetime":"2021-06-06T18:00+08:00","value":1.0},{"datetime":"2021-06-06T19:00+08:00","value":1.0},{"datetime":"2021-06-06T20:00+08:00","value":1.0},{"datetime":"2021-06-06T21:00+08:00","value":1.0},{"datetime":"2021-06-06T22:00+08:00","value":1.0},{"datetime":"2021-06-06T23:00+08:00","value":1.0},{"datetime":"2021-06-07T00:00+08:00","value":1.0},{"datetime":"2021-06-07T01:00+08:00","value":1.0},{"datetime":"2021-06-07T02:00+08:00","value":1.0},{"datetime":"2021-06-07T03:00+08:00","value":1.0},{"datetime":"2021-06-07T04:00+08:00","value":1.0},{"datetime":"2021-06-07T05:00+08:00","value":1.0},{"datetime":"2021-06-07T06:00+08:00","value":1.0},{"datetime":"2021-06-07T07:00+08:00","value":1.0},{"datetime":"2021-06-07T08:00+08:00","value":1.0},{"datetime":"2021-06-07T09:00+08:00","value":0.9},{"datetime":"2021-06-07T10:00+08:00","value":1.0},{"datetime":"2021-06-07T11:00+08:00","value":1.0},{"datetime":"2021-06-07T12:00+08:00","value":1.0},{"datetime":"2021-06-07T13:00+08:00","value":1.0},{"datetime":"2021-06-07T14:00+08:00","value":1.0}],
        "skycon":[{"datetime":"2021-06-05T15:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-05T16:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-05T17:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-05T18:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-05T19:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-05T20:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-05T21:00+08:00","value":"STORM_RAIN"},{"datetime":"2021-06-05T22:00+08:00","value":"HEAVY_RAIN"},{"datetime":"2021-06-05T23:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-06T00:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-06T01:00+08:00","value":"HEAVY_RAIN"},{"datetime":"2021-06-06T02:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-06T03:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T04:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T05:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T06:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T07:00+08:00","value":"MODERATE_RAIN"},{"datetime":"2021-06-06T08:00+08:00","value":"HEAVY_RAIN"},{"datetime":"2021-06-06T09:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T10:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T11:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T12:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T13:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T14:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T15:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T16:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T17:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T18:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T19:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T20:00+08:00","value":"HEAVY_RAIN"},{"datetime":"2021-06-06T21:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-06T22:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-06T23:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T00:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-07T01:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T02:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T03:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T04:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T05:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T06:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T07:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T08:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T09:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T10:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-07T11:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-07T12:00+08:00","value":"LIGHT_RAIN"},{"datetime":"2021-06-07T13:00+08:00","value":"CLOUDY"},{"datetime":"2021-06-07T14:00+08:00","value":"CLOUDY"}],
        "pressure":[{"datetime":"2021-06-05T15:00+08:00","value":98659.2},{"datetime":"2021-06-05T16:00+08:00","value":98611.2},{"datetime":"2021-06-05T17:00+08:00","value":98691.2},{"datetime":"2021-06-05T18:00+08:00","value":98710.4},{"datetime":"2021-06-05T19:00+08:00","value":98790.4000000001},{"datetime":"2021-06-05T20:00+08:00","value":98870.4000000001},{"datetime":"2021-06-05T21:00+08:00","value":98899.2},{"datetime":"2021-06-05T22:00+08:00","value":98918.4000000001},{"datetime":"2021-06-05T23:00+08:00","value":98899.2000000001},{"datetime":"2021-06-06T00:00+08:00","value":98851.2000000001},{"datetime":"2021-06-06T01:00+08:00","value":98771.2000000001},{"datetime":"2021-06-06T02:00+08:00","value":98739.2000000001},{"datetime":"2021-06-06T03:00+08:00","value":98691.2000000001},{"datetime":"2021-06-06T04:00+08:00","value":98729.6},{"datetime":"2021-06-06T05:00+08:00","value":98771.2000000001},{"datetime":"2021-06-06T06:00+08:00","value":98851.2000000001},{"datetime":"2021-06-06T07:00+08:00","value":98870.4000000001},{"datetime":"2021-06-06T08:00+08:00","value":98931.2000000001},{"datetime":"2021-06-06T09:00+08:00","value":98931.2000000001},{"datetime":"2021-06-06T10:00+08:00","value":98950.4},{"datetime":"2021-06-06T11:00+08:00","value":98950.4},{"datetime":"2021-06-06T12:00+08:00","value":98979.2000000001},{"datetime":"2021-06-06T13:00+08:00","value":98931.2000000001},{"datetime":"2021-06-06T14:00+08:00","value":98950.4000000001},{"datetime":"2021-06-06T15:00+08:00","value":98950.4000000001},{"datetime":"2021-06-06T16:00+08:00","value":98979.2000000001},{"datetime":"2021-06-06T17:00+08:00","value":98992.0000000001},{"datetime":"2021-06-06T18:00+08:00","value":99059.2000000001},{"datetime":"2021-06-06T19:00+08:00","value":99030.4000000001},{"datetime":"2021-06-06T20:00+08:00","value":99139.2000000001},{"datetime":"2021-06-06T21:00+08:00","value":99152.0000000001},{"datetime":"2021-06-06T22:00+08:00","value":99219.2000000001},{"datetime":"2021-06-06T23:00+08:00","value":99152.0},{"datetime":"2021-06-07T00:00+08:00","value":99139.2000000001},{"datetime":"2021-06-07T01:00+08:00","value":99110.4000000001},{"datetime":"2021-06-07T02:00+08:00","value":99091.2000000001},{"datetime":"2021-06-07T03:00+08:00","value":99110.4},{"datetime":"2021-06-07T04:00+08:00","value":99110.4},{"datetime":"2021-06-07T05:00+08:00","value":99110.4000000001},{"datetime":"2021-06-07T06:00+08:00","value":99190.4000000001},{"datetime":"2021-06-07T07:00+08:00","value":99251.2},{"datetime":"2021-06-07T08:00+08:00","value":99270.4},{"datetime":"2021-06-07T09:00+08:00","value":99238.4000000001},{"datetime":"2021-06-07T10:00+08:00","value":99270.4000000001},{"datetime":"2021-06-07T11:00+08:00","value":99251.2000000001},{"datetime":"2021-06-07T12:00+08:00","value":99190.4},{"datetime":"2021-06-07T13:00+08:00","value":99158.4000000001},{"datetime":"2021-06-07T14:00+08:00","value":99110.4000000001}],
        "visibility":[{"datetime":"2021-06-05T15:00+08:00","value":7.6},{"datetime":"2021-06-05T16:00+08:00","value":7.32},{"datetime":"2021-06-05T17:00+08:00","value":7.4},{"datetime":"2021-06-05T18:00+08:00","value":7.64},{"datetime":"2021-06-05T19:00+08:00","value":7.5},{"datetime":"2021-06-05T20:00+08:00","value":7.31},{"datetime":"2021-06-05T21:00+08:00","value":6.94},{"datetime":"2021-06-05T22:00+08:00","value":6.96},{"datetime":"2021-06-05T23:00+08:00","value":6.85},{"datetime":"2021-06-06T00:00+08:00","value":6.96},{"datetime":"2021-06-06T01:00+08:00","value":7.04},{"datetime":"2021-06-06T02:00+08:00","value":7.0},{"datetime":"2021-06-06T03:00+08:00","value":6.85},{"datetime":"2021-06-06T04:00+08:00","value":6.78},{"datetime":"2021-06-06T05:00+08:00","value":6.67},{"datetime":"2021-06-06T06:00+08:00","value":7.01},{"datetime":"2021-06-06T07:00+08:00","value":7.1},{"datetime":"2021-06-06T08:00+08:00","value":7.2},{"datetime":"2021-06-06T09:00+08:00","value":7.16},{"datetime":"2021-06-06T10:00+08:00","value":7.25},{"datetime":"2021-06-06T11:00+08:00","value":7.62},{"datetime":"2021-06-06T12:00+08:00","value":8.26},{"datetime":"2021-06-06T13:00+08:00","value":8.67},{"datetime":"2021-06-06T14:00+08:00","value":8.93},{"datetime":"2021-06-06T15:00+08:00","value":8.45},{"datetime":"2021-06-06T16:00+08:00","value":7.87},{"datetime":"2021-06-06T17:00+08:00","value":7.84},{"datetime":"2021-06-06T18:00+08:00","value":7.95},{"datetime":"2021-06-06T19:00+08:00","value":7.92},{"datetime":"2021-06-06T20:00+08:00","value":7.96},{"datetime":"2021-06-06T21:00+08:00","value":7.92},{"datetime":"2021-06-06T22:00+08:00","value":8.01},{"datetime":"2021-06-06T23:00+08:00","value":7.97},{"datetime":"2021-06-07T00:00+08:00","value":8.24},{"datetime":"2021-06-07T01:00+08:00","value":8.34},{"datetime":"2021-06-07T02:00+08:00","value":8.31},{"datetime":"2021-06-07T03:00+08:00","value":8.27},{"datetime":"2021-06-07T04:00+08:00","value":8.13},{"datetime":"2021-06-07T05:00+08:00","value":8.03},{"datetime":"2021-06-07T06:00+08:00","value":8.18},{"datetime":"2021-06-07T07:00+08:00","value":8.39},{"datetime":"2021-06-07T08:00+08:00","value":8.52},{"datetime":"2021-06-07T09:00+08:00","value":8.77},{"datetime":"2021-06-07T10:00+08:00","value":9.04},{"datetime":"2021-06-07T11:00+08:00","value":9.24},{"datetime":"2021-06-07T12:00+08:00","value":9.43},{"datetime":"2021-06-07T13:00+08:00","value":9.59},{"datetime":"2021-06-07T14:00+08:00","value":9.78}],
        "dswrf":[{"datetime":"2021-06-05T15:00+08:00","value":50.36},{"datetime":"2021-06-05T16:00+08:00","value":37.0},{"datetime":"2021-06-05T17:00+08:00","value":28.56},{"datetime":"2021-06-05T18:00+08:00","value":23.48},{"datetime":"2021-06-05T19:00+08:00","value":19.36},{"datetime":"2021-06-05T20:00+08:00","value":16.0},{"datetime":"2021-06-05T21:00+08:00","value":0.0},{"datetime":"2021-06-05T22:00+08:00","value":0.0},{"datetime":"2021-06-05T23:00+08:00","value":0.0},{"datetime":"2021-06-06T00:00+08:00","value":0.0},{"datetime":"2021-06-06T01:00+08:00","value":0.0},{"datetime":"2021-06-06T02:00+08:00","value":0.0},{"datetime":"2021-06-06T03:00+08:00","value":0.0},{"datetime":"2021-06-06T04:00+08:00","value":0.0},{"datetime":"2021-06-06T05:00+08:00","value":0.0},{"datetime":"2021-06-06T06:00+08:00","value":0.0},{"datetime":"2021-06-06T07:00+08:00","value":1.36},{"datetime":"2021-06-06T08:00+08:00","value":3.96},{"datetime":"2021-06-06T09:00+08:00","value":100.12},{"datetime":"2021-06-06T10:00+08:00","value":33.52},{"datetime":"2021-06-06T11:00+08:00","value":41.36},{"datetime":"2021-06-06T12:00+08:00","value":51.44},{"datetime":"2021-06-06T13:00+08:00","value":58.84},{"datetime":"2021-06-06T14:00+08:00","value":76.8},{"datetime":"2021-06-06T15:00+08:00","value":158.44},{"datetime":"2021-06-06T16:00+08:00","value":125.84},{"datetime":"2021-06-06T17:00+08:00","value":102.56},{"datetime":"2021-06-06T18:00+08:00","value":86.12},{"datetime":"2021-06-06T19:00+08:00","value":49.04},{"datetime":"2021-06-06T20:00+08:00","value":58.88},{"datetime":"2021-06-06T21:00+08:00","value":0.0},{"datetime":"2021-06-06T22:00+08:00","value":0.0},{"datetime":"2021-06-06T23:00+08:00","value":0.0},{"datetime":"2021-06-07T00:00+08:00","value":0.0},{"datetime":"2021-06-07T01:00+08:00","value":0.0},{"datetime":"2021-06-07T02:00+08:00","value":0.0},{"datetime":"2021-06-07T03:00+08:00","value":0.0},{"datetime":"2021-06-07T04:00+08:00","value":0.0},{"datetime":"2021-06-07T05:00+08:00","value":0.0},{"datetime":"2021-06-07T06:00+08:00","value":3.68},{"datetime":"2021-06-07T07:00+08:00","value":16.68},{"datetime":"2021-06-07T08:00+08:00","value":37.0},{"datetime":"2021-06-07T09:00+08:00","value":222.32},{"datetime":"2021-06-07T10:00+08:00","value":324.4},{"datetime":"2021-06-07T11:00+08:00","value":352.28},{"datetime":"2021-06-07T12:00+08:00","value":371.44},{"datetime":"2021-06-07T13:00+08:00","value":381.36},{"datetime":"2021-06-07T14:00+08:00","value":383.76}],
        "air_quality":{"aqi":[{"datetime":"2021-06-05T15:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-05T16:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-05T17:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-05T18:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-05T19:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-05T20:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-05T21:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-05T22:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-05T23:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T00:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T01:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T02:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T03:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T04:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T05:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T06:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T07:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T08:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T09:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T10:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T11:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T12:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T13:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T14:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T15:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T16:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T17:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T18:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-06T19:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T20:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-06T21:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-06T22:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-06T23:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T00:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T01:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T02:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T03:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T04:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T05:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T06:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T07:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T08:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T09:00+08:00","value":{"chn":10,"usa":10}},{"datetime":"2021-06-07T10:00+08:00","value":{"chn":9,"usa":9}},{"datetime":"2021-06-07T11:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-07T12:00+08:00","value":{"chn":8,"usa":8}},{"datetime":"2021-06-07T13:00+08:00","value":{"chn":7,"usa":7}},{"datetime":"2021-06-07T14:00+08:00","value":{"chn":7,"usa":7}}],
                        "pm25":[{"datetime":"2021-06-05T15:00+08:00","value":4},{"datetime":"2021-06-05T16:00+08:00","value":3},{"datetime":"2021-06-05T17:00+08:00","value":3},{"datetime":"2021-06-05T18:00+08:00","value":4},{"datetime":"2021-06-05T19:00+08:00","value":4},{"datetime":"2021-06-05T20:00+08:00","value":4},{"datetime":"2021-06-05T21:00+08:00","value":5},{"datetime":"2021-06-05T22:00+08:00","value":5},{"datetime":"2021-06-05T23:00+08:00","value":5},{"datetime":"2021-06-06T00:00+08:00","value":5},{"datetime":"2021-06-06T01:00+08:00","value":4},{"datetime":"2021-06-06T02:00+08:00","value":5},{"datetime":"2021-06-06T03:00+08:00","value":5},{"datetime":"2021-06-06T04:00+08:00","value":4},{"datetime":"2021-06-06T05:00+08:00","value":4},{"datetime":"2021-06-06T06:00+08:00","value":5},{"datetime":"2021-06-06T07:00+08:00","value":5},{"datetime":"2021-06-06T08:00+08:00","value":5},{"datetime":"2021-06-06T09:00+08:00","value":6},{"datetime":"2021-06-06T10:00+08:00","value":5},{"datetime":"2021-06-06T11:00+08:00","value":5},{"datetime":"2021-06-06T12:00+08:00","value":4},{"datetime":"2021-06-06T13:00+08:00","value":4},{"datetime":"2021-06-06T14:00+08:00","value":4},{"datetime":"2021-06-06T15:00+08:00","value":3},{"datetime":"2021-06-06T16:00+08:00","value":4},{"datetime":"2021-06-06T17:00+08:00","value":4},{"datetime":"2021-06-06T18:00+08:00","value":5},{"datetime":"2021-06-06T19:00+08:00","value":5},{"datetime":"2021-06-06T20:00+08:00","value":6},{"datetime":"2021-06-06T21:00+08:00","value":6},{"datetime":"2021-06-06T22:00+08:00","value":7},{"datetime":"2021-06-06T23:00+08:00","value":7},{"datetime":"2021-06-07T00:00+08:00","value":7},{"datetime":"2021-06-07T01:00+08:00","value":7},{"datetime":"2021-06-07T02:00+08:00","value":7},{"datetime":"2021-06-07T03:00+08:00","value":6},{"datetime":"2021-06-07T04:00+08:00","value":6},{"datetime":"2021-06-07T05:00+08:00","value":6},{"datetime":"2021-06-07T06:00+08:00","value":6},{"datetime":"2021-06-07T07:00+08:00","value":7},{"datetime":"2021-06-07T08:00+08:00","value":7},{"datetime":"2021-06-07T09:00+08:00","value":7},{"datetime":"2021-06-07T10:00+08:00","value":7},{"datetime":"2021-06-07T11:00+08:00","value":6},{"datetime":"2021-06-07T12:00+08:00","value":5},{"datetime":"2021-06-07T13:00+08:00","value":5},{"datetime":"2021-06-07T14:00+08:00","value":5}]}},
        "primary":0,"forecast_keypoint":"\u5c0f\u96e8\uff0c\u4eca\u5929\u665a\u95f420\u70b9\u949f\u540e\u96e8\u505c\uff0c\u8f6c\u9634\uff0c\u5176\u540e\u4e2d\u96e8"}}

 */





}
