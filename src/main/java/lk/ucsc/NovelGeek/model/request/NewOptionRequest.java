package lk.ucsc.NovelGeek.model.request;

public class NewOptionRequest {
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    private Long score = 0L;
}
