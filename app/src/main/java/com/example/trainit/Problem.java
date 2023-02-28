package com.example.trainit;

public class Problem {
    private String problem, answer1, answer2, answer3, answer4, correct;


    public Problem(String problem, String answer1, String answer2, String answer3, String answer4, String correct) {
        this.problem = problem;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct = correct;
    }

    public String getProblem() {
        return problem;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getCorrect() {
        return correct;
    }
}
