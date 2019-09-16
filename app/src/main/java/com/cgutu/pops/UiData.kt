package com.cgutu.pops

interface RecyclerItem {
    fun isSameItem(other: RecyclerItem): Boolean
    fun isSameContent(other: RecyclerItem): Boolean
    fun getType(): Int
}

sealed class OrderItemRecyclerItem : RecyclerItem

data class OrderItem(
    val id: String,
    val email: String,
    val price: String,
    val date: String,
    val status: String
) :
    OrderItemRecyclerItem() {
    override fun isSameItem(other: RecyclerItem): Boolean = other is OrderItem
    override fun isSameContent(
        other: RecyclerItem
    ): Boolean = other is OrderItem && other.id == id

    override fun getType(): Int = 0

}