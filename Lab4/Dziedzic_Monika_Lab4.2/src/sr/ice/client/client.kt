package sr.ice.client

import SmartHome.DevicePowerState
import SmartHome.IChocolateMachinePrx
import SmartHome.IRoomControllerPrx
import SmartHome.ITeaMachinePrx
import com.zeroc.Ice.Communicator
import com.zeroc.Ice.Util
import kotlin.system.exitProcess

enum class Machines(val machineName: String, val prompt: String) {
    CHOCOLATE_MACHINE("chocolateMachine", "CM> "),
    TEA_MACHINE("teaMachine", "TM> "),
    ROOM_CONTROLLER_MACHINE("1", "RCM> ")
}

const val incorrectParamsErrorMessage = "Incorrect params. Try again"

fun main(args: Array<String>) {
    val availableDeviceTypes = Machines.values().map { e -> e.machineName }

    val communicator: Communicator = Util.initialize(args)

    val chocolateMachine = communicator.stringToProxy("machines/" + Machines.CHOCOLATE_MACHINE.machineName + ":tcp -h localhost -p 10000")
    val teaMachine = communicator.stringToProxy("machines/" + Machines.TEA_MACHINE.machineName + ":tcp -h localhost -p 10000")
    val roomControllerMachine = communicator.stringToProxy("machines/" + Machines.ROOM_CONTROLLER_MACHINE.machineName + ":tcp -h localhost -p 10000")

    val chocolatePrx: IChocolateMachinePrx = IChocolateMachinePrx.checkedCast(chocolateMachine)
            ?: throw Error("Invalid " + Machines.CHOCOLATE_MACHINE.machineName + " proxy")

    val teaPrx: ITeaMachinePrx = ITeaMachinePrx.checkedCast(teaMachine)
            ?: throw Error("Invalid " + Machines.TEA_MACHINE.machineName + " proxy")

    val roomPrx: IRoomControllerPrx = IRoomControllerPrx.checkedCast(roomControllerMachine)
            ?: throw Error("Invalid " + Machines.ROOM_CONTROLLER_MACHINE.machineName + " proxy")

    printDescription(availableDeviceTypes)
    var line = readLine()
    try {
        while (line != "quit") {
            if (line != null) {
                when (line.trim()) {
                    Machines.CHOCOLATE_MACHINE.machineName -> {
                        doYourJobForChocolate(chocolatePrx)
                    }
                    Machines.TEA_MACHINE.machineName -> {
                        doYourJobForTea(teaPrx)
                    }
                    Machines.ROOM_CONTROLLER_MACHINE.machineName -> {
                        doYourJobForRoom(roomPrx)
                    }
                    else -> {
                        print("Unknown command. Try again!")
                    }
                }
            } else {
                break
            }
            printDescription(availableDeviceTypes)
            line = readLine()
        }
        print("Exited")
        communicator.destroy()
    } catch (e: Exception) {
        System.err.println(e)
    }
    exitProcess(0)
}

fun printDescription(availableDeviceTypes: List<String>) {
    println("Choose your device: ")
    print(availableDeviceTypes)
    println(".\nType 'quit' to exit.")
}

fun doYourJobForRoom(roomPrx: IRoomControllerPrx) {
    println("You chose " + Machines.ROOM_CONTROLLER_MACHINE.machineName);
    println("Type 'help' to get available commands")
    print(Machines.ROOM_CONTROLLER_MACHINE.prompt)
    var command = readLine()
    if (command == null)
        return
    else command = command.trim()

    while (command != "done") {
        if (command == null)
            break
        when (command) {
            "setParams" -> {
                print("Waiting for params: roomId, parameterType, parameterValue ")

                val params = readLine() ?: return  // if empty, exit this command
                val paramsList = params.split(" ")
                if (paramsList.size < 3) {
                    println(incorrectParamsErrorMessage)
                } else {
                    val room = roomPrx.setParameter(paramsList[0], paramsList[1], paramsList[2].toShort());
                    println("I've set for you your ${room.roomNameId} ROOM params to " +
                            "${room.parameterType}" +
                            "\"${room.parameterValue} ")
                }
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)
                command = readLine();
            }
            "on" -> {
                println(roomPrx.setDevicePowerState(DevicePowerState.ON))
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)

                command = readLine();
            }
            "off" -> {
                println(roomPrx.setDevicePowerState(DevicePowerState.OFF))
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)

                command = readLine();
            }
            "help" -> {
                println(roomPrx.availableCommands)
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)

                command = readLine();
            }
            "params" -> {
                println(roomPrx.currentDeviceParameters)
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)

                command = readLine();
            }
            "done" -> {
                return
            }
            else -> {
                print("Unknown command: $command")
                print(Machines.ROOM_CONTROLLER_MACHINE.prompt)

                command = readLine();
            }
        }
    }
}

fun doYourJobForChocolate(chocolatePrx: IChocolateMachinePrx) {
    println("You chose " + Machines.CHOCOLATE_MACHINE.machineName);
    println("Type 'help' to get available commands")
    print(Machines.CHOCOLATE_MACHINE.prompt)
    var command = readLine()?.trim()
    while (command != "done") {
        if (command == null)
            break
        when (command) {
            "makeChocolate" -> {
                print("Waiting for params: chocolateType, temperature ")

                val params = readLine() ?: return  // if empty, exit this command
                val paramsList = params.split(" ")
                if (paramsList.size < 2) {
                    println(incorrectParamsErrorMessage)
                } else {
                    val chocolate = chocolatePrx.makeChocolate(paramsList[0], paramsList[1].toShort());
                    println("I've made for you ${chocolate.chocolateType.name} CHOCOLATE " +
                            "of ${chocolate.drink.temperature} C")
                }
                print(Machines.CHOCOLATE_MACHINE.prompt)
                command = readLine();
            }
            "on" -> {
                println(chocolatePrx.setDevicePowerState(DevicePowerState.ON))
                print(Machines.CHOCOLATE_MACHINE.prompt)

                command = readLine();
            }
            "off" -> {
                println(chocolatePrx.setDevicePowerState(DevicePowerState.OFF))
                print(Machines.CHOCOLATE_MACHINE.prompt)

                command = readLine();
            }
            "help" -> {
                println(chocolatePrx.availableCommands)
                print(Machines.CHOCOLATE_MACHINE.prompt)

                command = readLine();
            }
            "params" -> {
                println(chocolatePrx.currentDeviceParameters)
                print(Machines.CHOCOLATE_MACHINE.prompt)

                command = readLine();
            }
            else -> {
                print("Unknown command: $command")
                print(Machines.CHOCOLATE_MACHINE.prompt)

                command = readLine();
            }
        }
    }
}

fun doYourJobForTea(teaPrx: ITeaMachinePrx) {
    println("You chose " + Machines.TEA_MACHINE.machineName);
    println("Type 'help' to get available commands")
    print(Machines.TEA_MACHINE.prompt)
    var command = readLine()?.trim()
    while (command != "done") {
        if (command == null)
            break
        when (command) {
            "makeTea" -> {
                print("Waiting for params: teaType, temperature, sugarLevel ")

                val params = readLine() ?: return  // if empty, exit this command
                val paramsList = params.split(" ")
                if (paramsList.size < 3) {
                    println(incorrectParamsErrorMessage)
                } else {
                    val tea = teaPrx.makeTea(paramsList[0], paramsList[1].toShort(), paramsList[2].toShort());
                    println("I've made for you ${tea.teaType.name} TEA " +
                            "of ${tea.drink.temperature} C and sugar level: ${tea.sugarLevel}")
                }
                print(Machines.TEA_MACHINE.prompt)
                command = readLine();
            }
            "on" -> {
                println(teaPrx.setDevicePowerState(DevicePowerState.ON))
                print(Machines.TEA_MACHINE.prompt)

                command = readLine();
            }
            "off" -> {
                println(teaPrx.setDevicePowerState(DevicePowerState.OFF))
                print(Machines.TEA_MACHINE.prompt)

                command = readLine();
            }
            "help" -> {
                println(teaPrx.availableCommands)
                print(Machines.TEA_MACHINE.prompt)

                command = readLine();
            }
            "params" -> {
                println(teaPrx.currentDeviceParameters)
                print(Machines.TEA_MACHINE.prompt)

                command = readLine();
            }
            else -> {
                print("Unknown command: $command")
                print(Machines.TEA_MACHINE.prompt)

                command = readLine();
            }
        }
    }
}