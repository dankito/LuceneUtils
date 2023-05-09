package net.dankito.utils.lucene.extensions

import java.time.Instant

/**
 * Microsecond precision should be enough for almost all cases (are are almost no clock with nanosecond precision)
 */
fun Instant.storedValue(): Long =
    this.epochSecond * 1_000_000 + this.nano / 1_1000