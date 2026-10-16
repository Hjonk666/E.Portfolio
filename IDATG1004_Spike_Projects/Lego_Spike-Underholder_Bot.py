import motor_pair

import color_sensor
import distance_sensor
from hub import light_matrix
from hub import port
from hub import light_matrix
from hub import sound
import random
import runloop


# Initialisering av motorpar (A = venstre, B = høyre)
motor_pair.pair(motor_pair.PAIR_1, port.A, port.B)

# Svart linje → reflekterer lite lys → lav verdi (ca. 10–30)
# Hvitt gulv → reflekterer mye lys → høy verdi (ca. 60–90)
#
# Derfor bruker vi en grenseverdi:
# svart < GRENSEVERDI
# hvitt > GRENSEVERDI
GRENSEVERDI = 40

#teller tid i sec
TID = 0

async def main():
    global TID
    while True:
        # 1. Hinderdeteksjon: stopper 5-10 cm foran hindring 
        dist = distance_sensor.distance(port.F)
        if dist >= 0 and dist <= 150:
            motor_pair.stop(motor_pair.PAIR_1)
            light_matrix.show_image(light_matrix.IMAGE_PACMAN)
            await sound.beep(523, 1000)
            break

        # --- Les refleksjon fra fargesensorene ---
        # Venstre sensor på port D
        left = color_sensor.reflection(port.D)

        # Høyre sensor på port C
        right = color_sensor.reflection(port.C)
        # 1. Begge sensorer ser hvitt → kjør rett fram
        
        if left > GRENSEVERDI and right > GRENSEVERDI:
            #motor_pair.move(motor_pair.PAIR_1, 0, velocity=-250)
            motor_pair.move_tank(motor_pair.PAIR_1, -180, -180)

        # 2. Venstre sensor ser svart → gjør en 90° venstresving
        elif left < GRENSEVERDI:
            #  venstresving ca 90 grader 
            ## previous one 
            #motor_pair.move(motor_pair.PAIR_1, 80, velocity=-250)
            # venstre sving – GLATT - den venstre motoren roterer førtere, men begge to roterer
            motor_pair.move_tank(motor_pair.PAIR_1, -250, 80)
            # Varighet på svingen
            await runloop.sleep_ms(400)

        # 3. Høyre sensor ser svart → liten korrigering til høyre KANSJE MÅ JUSTERES
        elif right < GRENSEVERDI:
            #motor_pair.move(motor_pair.PAIR_1, -60, velocity=-250)
            # høyre sving – GLATT - motsatt men ikke så star forskjell
            motor_pair.move_tank(motor_pair.PAIR_1, 80, -100)
            


        # Liten pause for stabil kjøring
        await runloop.sleep_ms(100)
        TID = TID + 0.1

        if TID >=10:
            #1.stopper hver 10.sec
            motor_pair.stop(motor_pair.PAIR_1)
            await runloop.sleep_ms(300)
            #2. velger en aktivitet
            valg = random.randint(1,4)
            if valg == 1:
                # lyd
                await sound.beep(440, 500)
                await sound.beep(440, 500)
                await sound.beep(440, 500)
            elif valg == 2:
                await light_matrix.write("WOOHO")
                await runloop.sleep_ms(50)
            elif valg == 3:
                #fram og bak
                motor_pair.move(motor_pair.PAIR_1, -80, velocity=-200)
                await runloop.sleep_ms(300)
                motor_pair.move(motor_pair.PAIR_1, 80, velocity=-200)
                await runloop.sleep_ms(300)
                motor_pair.stop(motor_pair.PAIR_1)
            elif valg == 4:
                light_matrix.show_image(light_matrix.IMAGE_HAPPY)
                await runloop.sleep_ms(800)
                light_matrix.clear()

            TID = 0 #NULLSTILLE 


runloop.run(main())