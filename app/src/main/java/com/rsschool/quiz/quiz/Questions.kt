package com.rsschool.quiz.quiz

object Questions {
    data class Question(
        val question: String,
        val answers: HashMap<Int, String>,
        val rightAnswer: Int,
        var userAnswer: Int = -1
    )

    val questions = listOf(
        Question(
            "Gémini",
            hashMapOf(
                1 to "Единорог",
                2 to "Змея",
                3 to "Близнецы",
                4 to "Лебедь",
                5 to "Лев"
            ),
            3
        ),
        Question(
            "Cánis Májor",
            hashMapOf(
                1 to "Большая Медведица",
                2 to "Козерог",
                3 to "Гончие Псы",
                5 to "Большой Пес",
                4 to "Северная Корона"
            ),
            5
        ),

        Question(
            "Scútum",
            hashMapOf(
                1 to "Телец",
                2 to "Ящерица",
                3 to "Сетка",
                4 to "Щит",
                5 to "Рысь"
            ),
            4
        ),

        Question(
            "Vírgo",
            hashMapOf(
                1 to "Волк",
                3 to "Возничий",
                2 to "Дева",
                4 to "Живописец",
                5 to "Кит"
            ),
            2
        ),

        Question(
            "Ophiúchus",
            hashMapOf(
                4 to "Дельфин",
                2 to "Микроскоп",
                3 to "Паруса",
                1 to "Змееносец",
                5 to "Овен"
            ),
            1
        ),
    )
}