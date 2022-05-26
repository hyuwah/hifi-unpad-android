package unpad.fmipa.hifi.android.data.remote.mapper

interface Mapper<IN, OUT> {
    fun map(input: IN): OUT
}