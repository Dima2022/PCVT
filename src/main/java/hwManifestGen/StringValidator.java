//**********************************************************************
// (C) Copyright 2020-2021 Hewlett Packard Enterprise Development LP
// 
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
// OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.
//**********************************************************************

package hwManifestGen;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

/**
 * A simple utility that exposes a fluent way to validate Strings.  Can easily be generalized to
 * any type of data.  See example usage in StringValidationTest.
 */
public final class StringValidator {
    //private static final Logger DEFAULT_LOGGER = LogManager.getLogger();

    private final String value;
    private final String fieldName;
    private Logger logger;

    /**
     * Begins a validation operation.
     *
     * @param value the value to check
     * @param fieldName the name of the field (to be used in error reporting)
     * @return a Validation object, upon which validation methods can be called
     */
    public static StringValidator check(final String value, final String fieldName) {
        return new StringValidator(value, fieldName, null);
    }

    /**
     * Begins a validation operation.
     *
     * @param value the value to check
     * @param fieldName the name of the field (to be used in error reporting)
     * @param logger a logger to use in lieu of Validation's logger
     * @return a Validation object, upon which validation methods can be called
     */
    public static StringValidator check(final String value, final String fieldName,
                                        final Logger logger) {
        return new StringValidator(value, fieldName, logger);
    }

    private StringValidator(final String value, final String fieldName, final Logger logger) {
        this.logger = null;
		this.value = value;
        this.fieldName = fieldName;
        if (logger == null) {
            //this.logger = DEFAULT_LOGGER;
        } else {
            this.logger = logger;
        }
    }

    /**
     * Assert that the given field is not null.  Throws an IllegalArgumentException if the value
     * is indeed null.
     *
     * @return this Validation object for further validation
     */
    public StringValidator notNull() {
        if (value == null) {
            String message = String.format("Field %s is null", fieldName);
            //logger.error(message);
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Assert that the given field is not blank (empty or null.)  Throws an IllegalArgumentException
     * if the value is indeed blank.
     *
     * @return this Validation object for further validation
     */
    public StringValidator notBlank() {
        if (StringUtils.isBlank(value)) {
            String message = String.format("Field %s is blank (empty or null)", fieldName);
            //logger.error(message);
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Assert that the given field is not longer than the given value.  Throws an
     * IllegalArgumentException if the value exceeds this length.  A null value will pass
     * this validation.
     *
     * @param maxLength the maximum length of the String
     * @return this Validation object for further validation
     */
    public StringValidator maxLength(final int maxLength) {
        if (value == null) {
            return this;
        }

        if (value.length() > maxLength) {
            String message = String.format(
                    "Field %s is too large (%d > %d) with value %s",
                    fieldName, value.length(), maxLength, value
            );
            //logger.error(message);
            throw new IllegalArgumentException(message);
        }

        return this;
    }

    /**
     * Method to be called when validation is complete to return the (now-validated) object.
     *
     * @return the originally-provided value
     */
    public String get() {
        return value;
    }
}
