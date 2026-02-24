/*
 * Copyright (C) 2004-2007 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2007-2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */

package megamek.common.weapons.mgs.innerSphere;

import static megamek.common.game.IGame.LOGGER;

/**
 * @author Sebastian Brocks
 */
public class ISXMGA extends megamek.common.weapons.AmmoWeapon {
    @java.io.Serial
    private static final long serialVersionUID = 5247934062852001336L;

    public ISXMGA() {
        super();

        name = "Machine Gun Array (X)";
        addLookupName("IS Machine Gun Array (X)");
        setInternalName("ISXMGA");
        heat = 0;
        damage = 3;
        infDamageClass = megamek.common.equipment.WeaponType.WEAPON_BURST_6D6;
        rackSize = 2;
        ammoType = megamek.common.equipment.AmmoType.AmmoTypeEnum.MG;
        minimumRange = WEAPON_NA;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 0; // we'll have to calculate this in calculateBV(),
        // because it depends on the number of MGs linked to
        // the MGA
        flags = flags.or(F_MEK_WEAPON).or(F_TANK_WEAPON).or(F_AERO_WEAPON)
              .or(F_PROTO_WEAPON).or(F_BALLISTIC).or(F_BURST_FIRE).or(F_MGA);
        cost = 1250;
        String[] modeStrings = { "Linked", "Off" };
        setModes(modeStrings);
        instantModeSwitch = false;
        rulesRefs = "228, TM";
        techAdvancement.setTechBase(megamek.common.enums.TechBase.IS)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(megamek.common.enums.TechRating.E)
              .setAvailability(megamek.common.enums.AvailabilityValue.X, megamek.common.enums.AvailabilityValue.X, megamek.common.enums.AvailabilityValue.F, megamek.common.enums.AvailabilityValue.F)
              .setISAdvancement(3066, 3068, 3070, DATE_NONE, DATE_NONE)
              .setISApproximate(true, false, false, false, false)
              .setPrototypeFactions(megamek.common.enums.Faction.FS)
              .setProductionFactions(megamek.common.enums.Faction.TC);
    }

    @Override
    @megamek.common.annotations.Nullable
    public megamek.common.weapons.handlers.AttackHandler getCorrectHandler(megamek.common.ToHitData toHit, megamek.common.actions.WeaponAttackAction waa, megamek.common.game.Game game,
                                                                           megamek.server.totalWarfare.TWGameManager manager) {
        try {
            return new megamek.common.weapons.handlers.MGAWeaponHandler(toHit, waa, game, manager);
        } catch (megamek.common.loaders.EntityLoadingException ignored) {
            LOGGER.warn("Get Correct Handler - Attach Handler Received Null Entity.");
        }
        return null;


    }

    @Override
    public double getBattleForceDamage(int range, megamek.common.equipment.Mounted<?> fcs) {
        return 0;
    }

}
