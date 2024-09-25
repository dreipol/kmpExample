package ch.dreipol.kmpexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform