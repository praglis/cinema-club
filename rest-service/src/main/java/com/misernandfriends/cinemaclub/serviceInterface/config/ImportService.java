package com.misernandfriends.cinemaclub.serviceInterface.config;

import java.io.File;
import java.io.IOException;

public interface ImportService {
    void importPreferences(File moviesFile, File prefDirectory) throws IOException;
    void importUsers(File dictionary) throws IOException;
}
