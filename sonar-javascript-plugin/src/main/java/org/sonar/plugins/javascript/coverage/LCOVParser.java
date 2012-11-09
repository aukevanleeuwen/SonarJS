/*
 * Sonar JavaScript Plugin
 * Copyright (C) 2011 Eriks Nukis and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.javascript.coverage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses JsTestDriver file coverage report files (generated by
 * http://code.google.com/p/js-test-driver/source/browse/trunk/JsTestDriver/src/com/google/jstestdriver/coverage/LcovWriter.java
 * 
 * @author Eriks.Nukis
 * 
 */
public final class LCOVParser {

  private static final Logger LOG = LoggerFactory.getLogger(LCOVParser.class);

  public List<JavaScriptFileCoverage> parseFile(File file) {
    List<String> lines = new LinkedList<String>();
    try {
      lines = FileUtils.readLines(file);
    } catch (IOException e) {
      LOG.debug("Cound not read content from file: {}", file.getAbsolutePath(), e);
    }

    List<JavaScriptFileCoverage> coveredFiles = new LinkedList<JavaScriptFileCoverage>();

    JavaScriptFileCoverage fileCoverage = new JavaScriptFileCoverage();

    for (String line : lines) {
      if (line.indexOf("SF:") > -1) {
        fileCoverage = new JavaScriptFileCoverage();
        String filePath = line.substring(line.indexOf("SF:") + 3);

        fileCoverage.setFilePath(filePath);

      } else if (line.indexOf("DA:") > -1) {
        String execution = line.substring(line.indexOf("DA:") + 3);
        String executionCount = execution.substring(execution.indexOf(',') + 1);
        String lineNumber = execution.substring(0, execution.indexOf(','));

        fileCoverage.addLine(Integer.valueOf(lineNumber).intValue(), Integer.valueOf(executionCount).intValue());

      } else if (line.indexOf("end_of_record") > -1) {
        coveredFiles.add(fileCoverage);
      }
    }
    return coveredFiles;
  }
}
