package com.renatic.app.helper

class Stack<T> {
    private val stack = mutableListOf<T>()

    fun push(item: T) {
        stack.add(item)
    }

    fun pop(): T? {
        if (isEmpty()) {
            return null
        }
        val lastIndex = stack.lastIndex
        val item = stack[lastIndex]
        stack.removeAt(lastIndex)
        return item
    }

    fun peek(): T? {
        if (isEmpty()) {
            return null
        }
        return stack[stack.lastIndex]
    }

    fun isEmpty(): Boolean {
        return stack.isEmpty()
    }

    fun size(): Int {
        return stack.size
    }
}