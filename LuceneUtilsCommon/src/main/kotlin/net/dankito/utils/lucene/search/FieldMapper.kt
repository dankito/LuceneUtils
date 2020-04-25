package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.Constants
import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexableField
import java.math.BigDecimal
import java.util.*


open class FieldMapper {

	open fun string(document: Document, fieldName: String): String {
		return mapField(document, fieldName) { mapToString(it) }
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
		return mapNullableField(document, fieldName) { mapToString(it) }
	}

	open fun nullableString(result: SearchResult, fieldName: String): String? {
		return nullableString(result.document, fieldName)
	}

	open fun stringList(document: Document, fieldName: String): List<String> {
		return document.getFields(fieldName).map { mapToString(it) }
	}

	open fun stringList(result: SearchResult, fieldName: String): List<String> {
		return stringList(result.document, fieldName)
	}

	open fun nullableStringList(document: Document, fieldName: String): List<String>? {
		return document.getFields(fieldName)?.map { mapToString(it) }
	}

	open fun nullableStringList(result: SearchResult, fieldName: String): List<String>? {
		return nullableStringList(result.document, fieldName)
	}

	protected open fun mapToString(field: IndexableField): String {
		return field.stringValue()
	}


	open fun int(document: Document, fieldName: String): Int {
		return mapField(document, fieldName) { mapToInt(it) }
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
		return mapNullableField(document, fieldName) { mapToInt(it) }
	}

	open fun nullableInt(result: SearchResult, fieldName: String): Int? {
		return nullableInt(result.document, fieldName)
	}

	open fun intList(document: Document, fieldName: String): List<Int> {
		return mapFields(document, fieldName) { mapToInt(it) }
	}

	open fun intList(result: SearchResult, fieldName: String): List<Int> {
		return intList(result.document, fieldName)
	}

	open fun nullableIntList(document: Document, fieldName: String): List<Int>? {
		return document.getFields(fieldName)?.map { mapToInt(it) }
	}

	open fun nullableIntList(result: SearchResult, fieldName: String): List<Int>? {
		return nullableIntList(result.document, fieldName)
	}

	protected open fun mapToInt(field: IndexableField): Int {
		return mapToNumber(field).toInt()
	}


	open fun long(document: Document, fieldName: String): Long {
		return mapField(document, fieldName) { mapToLong(it) }
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
		return mapNullableField(document, fieldName) { mapToLong(it) }
	}

	open fun nullableLong(result: SearchResult, fieldName: String): Long? {
		return nullableLong(result.document, fieldName)
	}

	open fun longList(document: Document, fieldName: String): List<Long> {
		return mapFields(document, fieldName) { mapToLong(it) }
	}

	open fun longList(result: SearchResult, fieldName: String): List<Long> {
		return longList(result.document, fieldName)
	}

	open fun nullableLongList(document: Document, fieldName: String): List<Long>? {
		return document.getFields(fieldName)?.map { mapToLong(it) }
	}

	open fun nullableLongList(result: SearchResult, fieldName: String): List<Long>? {
		return nullableLongList(result.document, fieldName)
	}

	protected open fun mapToLong(field: IndexableField): Long {
		return mapToNumber(field).toLong()
	}


	open fun float(document: Document, fieldName: String): Float {
		return mapField(document, fieldName) { mapToFloat(it) }
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
		return mapNullableField(document, fieldName) { mapToFloat(it) }
	}

	open fun nullableFloat(result: SearchResult, fieldName: String): Float? {
		return nullableFloat(result.document, fieldName)
	}

	open fun floatList(document: Document, fieldName: String): List<Float> {
		return mapFields(document, fieldName) { mapToFloat(it) }
	}

	open fun floatList(result: SearchResult, fieldName: String): List<Float> {
		return floatList(result.document, fieldName)
	}

	open fun nullableFloatList(document: Document, fieldName: String): List<Float>? {
		return document.getFields(fieldName)?.map { mapToFloat(it) }
	}

	open fun nullableFloatList(result: SearchResult, fieldName: String): List<Float>? {
		return nullableFloatList(result.document, fieldName)
	}

	protected open fun mapToFloat(field: IndexableField): Float {
		return mapToNumber(field).toFloat()
	}


	open fun double(document: Document, fieldName: String): Double {
		return mapField(document, fieldName) { mapToDouble(it) }
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
		return mapNullableField(document, fieldName) { mapToDouble(it) }
	}

	open fun nullableDouble(result: SearchResult, fieldName: String): Double? {
		return nullableDouble(result.document, fieldName)
	}

	open fun doubleList(document: Document, fieldName: String): List<Double> {
		return mapFields(document, fieldName) { mapToDouble(it) }
	}

	open fun doubleList(result: SearchResult, fieldName: String): List<Double> {
		return doubleList(result.document, fieldName)
	}

	open fun nullableDoubleList(document: Document, fieldName: String): List<Double>? {
		return document.getFields(fieldName)?.map { mapToDouble(it) }
	}

	open fun nullableDoubleList(result: SearchResult, fieldName: String): List<Double>? {
		return nullableDoubleList(result.document, fieldName)
	}

	protected open fun mapToDouble(field: IndexableField): Double {
		return mapToNumber(field).toDouble()
	}


	open fun date(document: Document, fieldName: String): Date {
		return mapField(document, fieldName) { mapToDate(it) }
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
		return mapNullableField(document, fieldName) { mapToDate(it) }
	}

	open fun nullableDate(result: SearchResult, fieldName: String): Date? {
		return nullableDate(result.document, fieldName)
	}

	open fun dateList(document: Document, fieldName: String): List<Date> {
		return mapFields(document, fieldName) { mapToDate(it) }
	}

	open fun dateList(result: SearchResult, fieldName: String): List<Date> {
		return dateList(result.document, fieldName)
	}

	open fun nullableDateList(document: Document, fieldName: String): List<Date>? {
		return document.getFields(fieldName)?.map { mapToDate(it) }
	}

	open fun nullableDateList(result: SearchResult, fieldName: String): List<Date>? {
		return nullableDateList(result.document, fieldName)
	}

	protected open fun mapToDate(field: IndexableField): Date {
		return Date(mapToLong(field))
	}


	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimal(document: Document, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal {
		return mapField(document, fieldName) { mapToBigDecimal(it, precision) }
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimal(result: SearchResult, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal {
		return bigDecimal(result.document, fieldName, precision)
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimal(document: Document, fieldName: String, defaultValue: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal {
		return nullableBigDecimal(document, fieldName, precision) ?: defaultValue
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimal(result: SearchResult, fieldName: String, defaultValue: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal {
		return bigDecimal(result.document, fieldName, defaultValue, precision)
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableBigDecimal(document: Document, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal? {
		return mapNullableField(document, fieldName) { mapToBigDecimal(it, precision) }
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableDBigDecimal(result: SearchResult, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal? {
		return nullableBigDecimal(result.document, fieldName, precision)
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimalList(document: Document, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): List<BigDecimal> {
		return mapFields(document, fieldName) { mapToBigDecimal(it, precision) }
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun bigDecimalList(result: SearchResult, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): List<BigDecimal> {
		return bigDecimalList(result.document, fieldName, precision)
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableBigDecimalList(document: Document, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): List<BigDecimal>? {
		return document.getFields(fieldName)?.map { mapToBigDecimal(it, precision) }
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	open fun nullableBigDecimalList(result: SearchResult, fieldName: String, precision: Int = Constants.DefaultBigDecimalPrecision): List<BigDecimal>? {
		return nullableBigDecimalList(result.document, fieldName, precision)
	}

	/**
	 * BigDecimals are stored as Long.
	 *
	 * Therefore the precision (= number of decimal places) with which your BigDecimal are stored has to be specified.
	 * precision has to be set to the same value as with which BigDecimal got stored.
	 *
	 * The default is 2 which is accurate for most currencies.
	 */
	@JvmOverloads
	protected open fun mapToBigDecimal(field: IndexableField, precision: Int = Constants.DefaultBigDecimalPrecision): BigDecimal {
		return BigDecimal.valueOf(mapToLong(field), precision)
	}


	open fun number(document: Document, fieldName: String): Number {
		return mapField(document, fieldName) { mapToNumber(it) }
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
		return mapNullableField(document, fieldName) { mapToNumber(it) }
	}

	open fun nullableNumber(result: SearchResult, fieldName: String): Number? {
		return nullableNumber(result.document, fieldName)
	}

	protected open fun mapToNumber(field: IndexableField): Number {
		return field.numericValue()
	}


	open fun <T> mapField(document: Document, fieldName: String, mapper: (IndexableField) -> T): T {
		return mapper(document.getField(fieldName))
	}

	open fun <T> mapNullableField(document: Document, fieldName: String, mapper: (IndexableField) -> T): T? {
		return document.getField(fieldName)?.let { mapper(it) }
	}

	open fun <T> mapFields(document: Document, fieldName: String, mapper: (IndexableField) -> T): List<T> {
		return document.getFields(fieldName).map { mapper(it) }
	}

}