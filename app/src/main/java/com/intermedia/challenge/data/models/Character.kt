package com.intermedia.challenge.data.models

import com.google.gson.annotations.SerializedName

data class Character(
    val comics: Appearances = Appearances(),
    val description: String = "",
    val events: Appearances = Appearances(),
    val id: Int = 0,
    val name: String = "",
    val series: Appearances = Appearances(),
    val stories: Appearances = Appearances(),
    val thumbnail: Thumbnail = Thumbnail(),
    val urls: List<Url> = listOf()
)

data class Appearances(
    val available: Int = 0,
    val collectionURI: String = "",

    @SerializedName("items")
    val appearances: List<Appearance> = listOf(),

    val returned: Int = 0
)

data class Appearance(
    val name: String = "",
    val resourceURI: String = "",
    val type: String = ""
)



data class Url(
    val type: String = "",
    val url: String = ""
)


/*
{
    "code": "int",
    "status": "string",
    "copyright": "string",
    "attributionText": "string",
    "attributionHTML": "string",

    "data": {
    "offset": "int",
    "limit": "int",
    "total": "int",
    "count": "int",

    "results": [
    {
        "id": "int",
        "name": "string",
        "description": "string",
        "modified": "Date",
        "resourceURI": "string",

        "urls": [
        {
            "type": "string",
            "url": "string"
        }
        ],

        "thumbnail": {
        "path": "string",
        "extension": "string"
    },

        "comics": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",

        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]

        },


        "stories": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",

        "items": [
        {
            "resourceURI": "string",
            "name": "string",
            "type": "string"
        }
        ]

        },

        "events": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]
        },

        "series": {
        "available": "int",
        "returned": "int",
        "collectionURI": "string",
        "items": [
        {
            "resourceURI": "string",
            "name": "string"
        }
        ]
         }
    }
    ]
},
    "etag": "string"
}*/
