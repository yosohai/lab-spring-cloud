package org.lab.bugs.suppressedexceptions;

import static java.lang.System.err;

public class SuppressedExceptionDemoWithTryFinallyNew {
    /**
     * Executable member function demonstrating suppressed exceptions
     * Suppressed expression is added back in primary exception
     */
    public static void memberFunction() throws Exception {
        Throwable th = null;
        DirtyResource resource = new DirtyResource();
        try {
            resource.accessResource();
        } catch (Exception e) {
            th = e;
        } finally {
            try {
                resource.close();
            } catch (Exception e) {
                if (th != null) {
                    e.addSuppressed(th); //Add to primary exception
                }
                throw e;
            }
        }
    }

    /**
     * Executable function demonstrating suppressed exceptions.
     */
    public static void main(String[] arguments) throws Exception {
        try {
            memberFunction();
        } catch (Exception ex) {
            err.println("Exception encountered: " + ex.toString());
            final Throwable[] suppressedExceptions = ex.getSuppressed();
            final int numSuppressed = suppressedExceptions.length;
            if (numSuppressed > 0) {
                err.println("There are " + numSuppressed + " suppressed exceptions:");
                for (final Throwable exception : suppressedExceptions) {
                    err.println("Suppressed exception detail:" + exception.toString());
                }
            }
        }
    }
}
