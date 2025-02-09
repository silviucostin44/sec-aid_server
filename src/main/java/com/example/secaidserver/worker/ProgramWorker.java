package com.example.secaidserver.worker;

import com.example.secaidserver.model.program.Program;
import com.example.secaidserver.model.program.ProgramDB;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgramWorker {

    /**
     * Builds a list of programs attributes db objects.
     *
     * @param programs the list of programs to build from.
     * @return a list of built programs.
     */
    public List<ProgramDB> buildListOfProgramsDB(final List<Program> programs) {
        return programs.stream()
                       .map(ProgramDB::new)
                       .collect(Collectors.toList());
    }
}
