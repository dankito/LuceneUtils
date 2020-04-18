package net.dankito.utils.lucene.search

import org.apache.lucene.document.Document
import java.util.*


open class FieldMapper {

	open fun string(document: Document, fieldName: String): String {
		return document.getField(fieldName).stringValue()
	}

	open fun string(result: SearchResult, fieldName: String): String {
		return string(result.document, fieldName)
	}

	open fun string(document: Document, fieldName: String, defaultValue: String): String {
		return nullableString(document, fieldName) ?: defaultValue
	}

	open fun string(result: SearchResult, fieldName: String, defaultValue: String): String {
		return string(result.document, fieldName, defaultValue)
	}

	open fun nullableString(document: Document, fieldName: String): String? {
		return document.getField(fieldName)?.stringValue()
	}

	open fun nullableString(result: SearchResult, fieldName: String): String? {
		return nullableString(result.document, fieldName)
	}


	open fun int(document: Document, fieldName: String): Int {
		return number(document, fieldName).toInt()
	}

	open fun int(result: SearchResult, fieldName: String): Int {
		return int(result.document, fieldName)
	}

	open fun int(document: Document, fieldName: String, defaultValue: Int): Int {
		return nullableInt(document, fieldName) ?: defaultValue
	}

	open fun int(result: SearchResult, fieldName: String, defaultValue: Int): Int {
		return int(result.document, fieldName, defaultValue)
	}

	open fun nullableInt(document: Document, fieldName: String): Int? {
		return nullableNumber(document, fieldName)?.toInt()
	}

	open fun nullableInt(result: SearchResult, fieldName: String): Int? {
		return nullableInt(result.document, fieldName)
	}


	open fun long(document: Document, fieldName: String): Long {
		return number(document, fieldName).toLong()
	}

	open fun long(result: SearchResult, fieldName: String): Long {
		return long(result.document, fieldName)
	}

	open fun long(document: Document, fieldName: String, defaultValue: Long): Long {
		return nullableLong(document, fieldName) ?: defaultValue
	}

	open fun long(result: SearchResult, fieldName: String, defaultValue: Long): Long {
		return long(result.document, fieldName, defaultValue)
	}

	open fun nullableLong(document: Document, fieldName: String): Long? {
		return nullableNumber(document, fieldName)?.toLong()
	}

	open fun nullableLong(result: SearchResult, fieldName: String): Long? {
		return nullableLong(result.document, fieldName)
	}


	open fun float(document: Document, fieldName: String): Float {
		return number(document, fieldName).toFloat()
	}

	open fun float(result: SearchResult, fieldName: String): Float {
		return float(result.document, fieldName)
	}

	open fun float(document: Document, fieldName: String, defaultValue: Float): Float {
		return nullableFloat(document, fieldName) ?: defaultValue
	}

	open fun float(result: SearchResult, fieldName: String, defaultValue: Float): Float {
		return float(result.document, fieldName, defaultValue)
	}

	open fun nullableFloat(document: Document, fieldName: String): Float? {
		return nullableNumber(document, fieldName)?.toFloat()
	}

	open fun nullableFloat(result: SearchResult, fieldName: String): Float? {
		return nullableFloat(result.document, fieldName)
	}


	open fun double(document: Document, fieldName: String): Double {
		return number(document, fieldName).toDouble()
	}

	open fun double(result: SearchResult, fieldName: String): Double {
		return double(result.document, fieldName)
	}

	open fun double(document: Document, fieldName: String, defaultValue: Double): Double {
		return nullableDouble(document, fieldName) ?: defaultValue
	}

	open fun double(result: SearchResult, fieldName: String, defaultValue: Double): Double {
		return double(result.document, fieldName, defaultValue)
	}

	open fun nullableDouble(document: Document, fieldName: String): Double? {
		return nullableNumber(document, fieldName)?.toDouble()
	}

	open fun nullableDouble(result: SearchResult, fieldName: String): Double? {
		return nullableDouble(result.document, fieldName)
	}


	open fun date(document: Document, fieldName: String): Date {
		return Date(long(document, fieldName))
	}

	open fun date(result: SearchResult, fieldName: String): Date {
		return date(result.document, fieldName)
	}

	open fun date(document: Document, fieldName: String, defaultValue: Date): Date {
		return nullableDate(document, fieldName) ?: defaultValue
	}

	open fun date(result: SearchResult, fieldName: String, defaultValue: Date): Date {
		return date(result.document, fieldName, defaultValue)
	}

	open fun nullableDate(document: Document, fieldName: String): Date? {
		nullableLong(document, fieldName)?.let {
			return Date(it)
		}

		return null
	}

	open fun nullableDate(result: SearchResult, fieldName: String): Date? {
		return nullableDate(result.document, fieldName)
	}


	open fun number(document: Document, fieldName: String): Number {
		return document.getField(fieldName).numericValue()
	}

	open fun number(result: SearchResult, fieldName: String): Number {
		return number(result.document, fieldName)
	}

	open fun number(document: Document, fieldName: String, defaultValue: Number): Number {
		return nullableNumber(document, fieldName) ?: defaultValue
	}

	open fun number(result: SearchResult, fieldName: String, defaultValue: Number): Number {
		return number(result.document, fieldName, defaultValue)
	}

	open fun nullableNumber(document: Document, fieldName: String): Number? {
		return document.getField(fieldName)?.numericValue()
	}

	open fun nullableNumber(result: SearchResult, fieldName: String): Number? {
		return nullableNumber(result.document, fieldName)
	}

}