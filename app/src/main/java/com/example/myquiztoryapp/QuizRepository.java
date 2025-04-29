package com.example.myquiztoryapp;

import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private final List<Question> questionList;

    public QuizRepository() {
        questionList = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questionList.add(new Question("1. Who built the Great Pyramid of Giza?",
                "Djoser", "Khufu", "Tutankhamun", "Ramses II", 2));
        questionList.add(new Question("2. What was the capital of Egypt during the Middle Kingdom?",
                "Thebes", "Itjtawy", "Memphis", "Amarna", 2));
        questionList.add(new Question("3. Which pharaoh introduced monotheism in Egypt?",
                "Ramses II", "Thutmose III", "Akhenaten", "Seti I", 3));
        questionList.add(new Question("4. What writing system did Ancient Egyptians use?",
                "Hieroglyphs", "Cuneiform", "Greek Alphabet", "Latin", 1));
        questionList.add(new Question("5. What was the primary stone used in pyramid construction?",
                "Basalt", "Limestone", "Granite", "Sandstone", 2));
        questionList.add(new Question("6. Which period followed the collapse of the Old Kingdom?",
                "First Intermediate Period", "Middle Kingdom", "New Kingdom", "Second Intermediate Period", 1));
        questionList.add(new Question("7. Who deciphered the Rosetta Stone?",
                "Napoleon Bonaparte", "John Gardner Wilkinson", "Richard Lepsius", "Jean-François Champollion", 4));
        questionList.add(new Question("8. What was the purpose of a mastaba?",
                "Tomb for officials", "Storage building", "Royal palace", "Military fortress", 1));
        questionList.add(new Question("9. Which river was crucial for Egyptian civilization?",
                "Amazon", "Nile", "Tigris", "Euphrates", 2));
        questionList.add(new Question("10. What was the primary purpose of the pyramids built during the Old Kingdom of Egypt?",
                "Storage facilities for grain", "Royal tombs for pharaohs", "Temples for worshiping the sun god", "Military fortresses", 2));
    }

    public List<Question> getQuestions() {
        return questionList;
    }
}

