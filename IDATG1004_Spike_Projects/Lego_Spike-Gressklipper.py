from hub import port
from hub import light_matrix
from hub import sound

import distance_sensor
import force_sensor
import motor_pair
import runloop #runloop er hendelsesstrikken (event loop) i SPIKE Prime som styrer kjøringen av asynkrone funksjoner
            #Den trengs for å starte hovedprogrammet med runloop.run(main()) og for å håndtere pauser med runloop.sleep_ms() uten å blokkere prosessoren.


# async betyr at roboten kan samtidig gjøre flere oppgaver
# når vi vil at den skal først vente til den er helt ferdig men noe, brukes await
async def main():
    # innbygedne funskjon spike har, får å pair motorer
    motor_pair.pair(motor_pair.PAIR_1, port.A, port.B)

    # x skal telle antall ganger den treffer noe, er det mer en 4x, ber om hjelp og står stille
    obstruction = 0

    while force_sensor.force(port.E) == 0: # skjekker om false og skal bli i denne while loopen så lengde den er det
        # gressklipperen venter til force_sensor er trykket
        await runloop.sleep_ms(10)


    #kan skriver først ferdig og deretter setter hjulene i gang
    await light_matrix.write("Exercise 2")
    motor_pair.move(motor_pair.PAIR_1, 0, velocity=-300)

    while force_sensor.force(port.E) > 0: #sjekker om TRUE, og vurderer at vi har fortsatt fingeren på roboten og venter 0,1s før den sjekker igjen. Roboren er raskere en fingeren :=)
        await runloop.sleep_ms(20)

    while force_sensor.force(port.E) == 0:
        hindring = distance_sensor.distance(port.F)

        #not hindring < 0 // distance_sensor gir
        if hindring >= 0 and hindring < 200:

            # x øker hver gang den gressklipperen møter en hindring, og ikke klarer å komme seg unna. Etter det fjerde forsøket, vil den stoppe opp og gi fra seg et lydsignal.
            obstruction = obstruction + 1
            if x == 5:
                motor_pair.stop(motor_pair.PAIR_1)
                sound.beep(440, 500)
                sound.beep(440, 500)
                sound.beep(880, 1000)
                await light_matrix.write("HELP")
                break
            # 200 = degrees, 100 =    steering, bestemmer hvor skarp svingen skal bli, 100 betyr at roboten svinger på stedet rundt sin egen akse.
            await motor_pair.move_for_degrees(motor_pair.PAIR_1, 250, 100, velocity=-265)
            motor_pair.move(motor_pair.PAIR_1, 0, velocity=-300)
        else: # x skal vokse bare om den treffer en hindring mer enn 4x på rad, ellers skal den resettes/nullstilles
            obstruction = 0
        await runloop.sleep_ms(10)

    motor_pair.stop(motor_pair.PAIR_1)
    await light_matrix.write("Exercise done")

runloop.run(main())