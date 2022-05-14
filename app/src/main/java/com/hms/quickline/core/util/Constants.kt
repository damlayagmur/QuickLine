package com.hms.quickline.core.util

object Constants {

    const val CloudDbZoneName = "COMDBZone"

    const val VIDEO_WIDTH = 280
    const val VIDEO_HEIGHT = 200
    const val VIDEO_FPS = 20

    const val LOCAL_TRACK_ID = "local_track"
    const val LOCAL_STREAM_ID = "stream_track"

    const val DATA_CHANNEL_NAME = "sendDataChannel"

    const val MEETING_ID = "meetingID"
    const val IS_JOIN = "isJoin"
    const val ANSWER = "answer"
    const val ACTION_ANSWER = "actionAnswer"
    const val UID = "uid"
    const val DECLINE = "decline"
    const val ACTION_DECLINE = "actionDecline"

    enum class USERTYPE {
        OFFER_USER,
        ANSWER_USER
    }

    enum class TYPE {
        OFFER,
        ANSWER,
        END
    }
}

