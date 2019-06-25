package com.ac.exception;

import com.ac.constant.AcError;

import lombok.Getter;

/**
 * Custom RuntimeException specific only for Auto-Complete services
 *
 * @author sarvesh
 */
public class AutoCompleteSvcException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * see {@link com.ac.constant.AcError}
     */
    @Getter
    private final AcError error;

    /**
     * Added to hide the default public constructor.
     */
    @SuppressWarnings("unused")
    private AutoCompleteSvcException() {
        this.error = null;
    }

    /**
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     * @see java.lang.RuntimeException#RuntimeException(String)
     */
    public AutoCompleteSvcException(String message) {
        super(message);
        this.error = null;
    }

    /**
     * @param   error   AcError
     * @see java.lang.RuntimeException#RuntimeException(String)
     */
    public AutoCompleteSvcException(AcError error) {
        super(error.msg());
        this.error = error;
    }

    /**
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public AutoCompleteSvcException(String message, Throwable cause) {
        super(message, cause);
        this.error = null;
    }

    /**
     * @param  error   AcError
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public AutoCompleteSvcException(AcError error, Throwable cause) {
        super(error.msg(), cause);
        this.error = error;
    }

    /**
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @see java.lang.RuntimeException#RuntimeException(Throwable)
     */
    public AutoCompleteSvcException(Throwable cause) {
        super(cause);
        this.error = null;
    }

}
