import java.sql.PreparedStatement;

public class ModelAddition {
    private int teamFirst = 0;
    private int teamSecond = 0;
    private int[] fpScore = {};
    private int[] spScore = {};
    private int msScore = 0;
    private String matchDate = null;

    public ModelAddition() {
        int teamFirst = 0;
        int teamSecond = 0;
        int[] fpScore = {};
        int[] spScore = {};
        int msScore = 0;
        String matchDate = null;
    }

    ModelAddition(int teamFirst, int teamSecond, int[] fpScore, int[] spScore, String matchDate) {
        setTeamFirst(teamFirst);
        setTeamSecond(teamSecond);
        setFpScore(fpScore);
        setSpScore(spScore);
        setMatchDate(matchDate);
    }

    private int[] getSpScore() {
        return spScore;
    }

    private void setSpScore(int[] spScore) {
        this.spScore = spScore;
    }

    private int getSPTeamFirstScore() {
        int[] score = this.getSpScore();
        return score[0];
    }

    private int getSPTeamSecondScore() {
        int[] score = this.getSpScore();
        return score[1];
    }

    private int[] getFpScore() {
        return fpScore;
    }

    private void setFpScore(int[] fpScore) {
        this.fpScore = fpScore;
    }

    private int getFPTeamFirstScore() {
        int[] score = this.getFpScore();
        return score[0];
    }

    private int getFPTeamSecondScore() {
        int[] score = this.getFpScore();
        return score[1];
    }

    private int getTeamSecond() {
        return teamSecond;
    }

    private void setTeamSecond(int teamSecond) {
        this.teamSecond = teamSecond;
    }

    private int getTeamFirst() {
        return teamFirst;
    }

    private void setTeamFirst(int teamFirst) {
        this.teamFirst = teamFirst;
    }

    private String getMatchDate() {
        return matchDate;
    }

    private void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    private int getTeamFirstMS() {
        return (this.getFPTeamFirstScore() + this.getSPTeamFirstScore());
    }

    private int getTeamSecondMS() {
        return (this.getFPTeamSecondScore() + this.getSPTeamSecondScore());
    }

    public boolean save() {
        PreparedStatement state = null;
        int rs = 0;
        try {
            String query = String.format(
                    "INSERT INTO match_additions (team1_id, team2_id, fp_team1_score, fp_team2_score, sp_team1_score, sp_team2_score, ms_team1, ms_team2, match_date)"
                            + " VALUES( '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    this.getTeamFirst(), this.getTeamSecond(), this.getFPTeamFirstScore(), this.getFPTeamSecondScore(), this.getSPTeamFirstScore(), this.getSPTeamSecondScore(),
                    this.getTeamFirstMS(), this.getTeamSecondMS(), this.getMatchDate());

            Database db = new Database();
            state = db.connection.prepareStatement(query);
            rs = state.executeUpdate();

            return (rs > 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (state != null) {
                try {
                    state.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
