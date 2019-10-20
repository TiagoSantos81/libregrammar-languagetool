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

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.languagetool.rules.ga.DhaNoBeirtData.getNumberReplacements;
import static org.languagetool.rules.ga.DhaNoBeirtData.getDaoine;

public class DhaNoBeirtRule extends Rule {
  public DhaNoBeirtRule(ResourceBundle messages) {
    super.setCategory(Categories.MISC.getCategory(messages));
    setLocQualityIssueType(ITSIssueType.Misspelling);
    addExamplePair(Example.wrong("Tá <marker>dhá</marker> dheartháireacha agam."),
      Example.fixed("Tá <marker>beirt</marker> dheartháireacha agam."));
  }

  @Override
  public String getId() {
    return "GA_DHA_NO_BEIRT";
  }

  @Override
  public String getDescription() {
    return "'dhá' nó 'beirt'";
  }

  private boolean isLenitedNoun(AnalyzedTokenReadings readings) {
    for (AnalyzedToken tok : readings.getReadings()) {
      String posTag = tok.getPOSTag();
      if ((posTag.contains("Noun:") || posTag.contains("Subst:"))
          && posTag.contains(":Len")) {
        return true;
      }
    }
    return false;
  }
  private String getFirstLenitedLemma(AnalyzedTokenReadings readings) {
    for (AnalyzedToken tok : readings.getReadings()) {
      String posTag = tok.getPOSTag();
      if ((posTag.contains("Noun:") || posTag.contains("Subst:"))
        && posTag.contains(":Len")) {
        return tok.getLemma();
      }
    }
    return "";
  }
  private String[] getRestOfNP(AnalyzedTokenReadings[] tokens, int start, int end) {
    List<String> ret = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      ret.add(tokens[i].getToken());
    }
    return ret.toArray(new String[ret.size()]);
  }

  @Override
  public RuleMatch[] match(AnalyzedSentence sentence) {
    List<RuleMatch> ruleMatches = new ArrayList<>();
    AnalyzedTokenReadings[] tokens = sentence.getTokensWithoutWhitespace();
    int markEnd = 1;
    int prevTokenIndex = 0;
    String replacement = null;
    String msg = null;
    for (int i = 1; i < tokens.length; i++) {  // ignoring token 0, i.e., SENT_START
      if (isNumber(tokens[i]) && (i < tokens.length - 1 && isPerson(tokens[i + 1]))) {
        markEnd = i + 1;
        if ("dhá".equalsIgnoreCase(tokens[i].getToken())) {
          for (int j = i + 2; j < tokens.length; j++) {
            if ("déag".equalsIgnoreCase(tokens[j].getToken())) {
              markEnd = j;
              replacement = "dháréag";
              String[] msgTokens = getRestOfNP(tokens, i + 1, j);
              if (isLenitedNoun(tokens[i + 1])) {
                msgTokens[0] = getFirstLenitedLemma(tokens[i + 1]);
              }
              msg = "Ba chóir duit <suggestion>" + replacement + " " +
                String.join(" ", msgTokens) + "</suggestion> a scríobh";
            }
          }
        }
        if (replacement == null) {
          replacement = getNumberReplacements().get(tokens[i].getToken());
          if (msg == null) {
            msg = "Ba chóir duit <suggestion>" + replacement + "</suggestion> a scríobh";
          }
        }
      }
      if (msg != null) {
        RuleMatch match = new RuleMatch(
          this, sentence, tokens[prevTokenIndex].getStartPos(), tokens[markEnd].getStartPos()+tokens[markEnd].getEndPos(), msg, "Uimhir phearsanta");
        ruleMatches.add(match);
        msg = null;
      }
      prevTokenIndex = i;
    }
    return toRuleMatchArray(ruleMatches);
  }

  private boolean isNumber(AnalyzedTokenReadings tok) {
    for (String num : getNumberReplacements().keySet()) {
      if (num.equalsIgnoreCase(tok.getToken())) {
        return true;
      }
    }
    return false;
  }
  private boolean isPerson(AnalyzedTokenReadings tok) {
    if (getDaoine().contains(tok.getToken().toLowerCase())) {
      return true;
    }
    for (AnalyzedToken reading : tok.getReadings()) {
      if (getDaoine().contains(reading.getLemma())) {
        return true;
      }
    }
    return false;
  }
}
