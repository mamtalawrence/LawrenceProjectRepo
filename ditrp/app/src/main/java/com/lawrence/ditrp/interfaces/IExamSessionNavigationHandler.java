package com.lawrence.ditrp.interfaces;

public interface IExamSessionNavigationHandler {

    /**
     * Handled navigation to attempt to the next question either clicked on next button or finish the timing to
     * attempt the current question.
     */
    void handleNavigationOnNext();

    /**
     * Set the visibility of the next/previous button views
     *
     * @param shouldVisible true, if need to show otherwise false
     */
    void updatePreviousButtonStatus(boolean shouldVisible);
}
