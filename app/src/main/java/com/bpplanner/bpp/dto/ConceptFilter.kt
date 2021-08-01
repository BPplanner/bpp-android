package com.bpplanner.bpp.dto

data class ConceptFilter(
    val numPerson: Array<IdValuePairCheckable>,
    val gender: Array<IdValuePairCheckable>,
    val background: Array<IdValuePairCheckable>,
    val props: Array<IdValuePairCheckable>,
    val cloth: Array<IdValuePairCheckable>
){

    fun toRequest(): Request {
        return Request(
            toRequestHelper(numPerson),
            toRequestHelper(gender),
            toRequestHelper(background),
            toRequestHelper(props),
            toRequestHelper(cloth),
        )
    }

    private fun toRequestHelper(list: Array<IdValuePairCheckable>): Array<String>{
        return list.filter { item -> item.checked }
            .map { item -> item.id }
            .toTypedArray()

    }

    data class Request(
        val head_count: Array<String>?,
        val gender: Array<String>?,
        val background: Array<String>?,
        val prop: Array<String>?,
        val dress: Array<String>?
    )
}
