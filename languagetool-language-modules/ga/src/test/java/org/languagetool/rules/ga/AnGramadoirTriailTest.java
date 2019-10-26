/* LanguageTool, a natural language style checker
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules.ga;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Irish;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnGramadoirTriailTest {
  private DhaNoBeirtRule rule;
  private JLanguageTool langTool;
  private List<TriailError> errors;
  AnGramadoirTriailData data;

  @Before
  public void setUp() throws Exception {
    rule = new DhaNoBeirtRule(TestTools.getMessages("ga"));
    langTool = new JLanguageTool(new Irish());
    data = new AnGramadoirTriailData();
    errors = data.getErrors();
  }
  @Test
  public void testTriailData() throws IOException {
    assert(data != null);
    assert(errors != null);
  }

}
