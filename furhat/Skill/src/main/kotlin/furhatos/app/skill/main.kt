package furhatos.app.skill

import furhatos.app.skill.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class SkillSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
