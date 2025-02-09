package com.example.secaidserver.service;

import com.example.secaidserver.model.program.Program;
import com.example.secaidserver.model.program.ProgramDB;

import java.util.List;

public interface ProgramService {

    /**
     * Retrieves and builds a program with all of its components.
     *
     * @param id the program id.
     * @return the built program.
     */
    ProgramDB getProgram(final Long id);

    /**
     * Saves a program with all components to db.
     *
     * @param program the program to save.
     * @return the saved program.
     */
    ProgramDB saveProgram(final Program program);

    /**
     * Retrieves all the programs in the db, mandatory fields name and id.
     *
     * @return the list of programs.
     */
    List<ProgramDB> getAllPrograms();

    /**
     * Saves a list of new empty programs on db.
     * These programs have only their name set.
     *
     * @param itemsToAdd the programs to save.
     */
    void addPrograms(final List<Program> itemsToAdd);

    /**
     * Edit the name of a list of programs.
     *
     * @param itemsToEdit the programs to edit.
     */
    void editPrograms(final List<Program> itemsToEdit);

    /**
     * Deletes a list of programs.
     *
     * @param itemsToDelete the programs to delete.
     */
    void deletePrograms(final List<Program> itemsToDelete);

    /**
     * Finds and retrieve the last step of the given program.
     *
     * @param programId the program id.
     * @return the last step the program was in.
     */
    int getLastProgramStep(final Long programId);
}
