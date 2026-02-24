/*
 * Copyright (C) 2004, 2005 Ben Mazur (bmazur@sev.org)
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

package megamek.common.weapons.mgs.clan;

/**
 * @author Andrew Hunter
 * @since Oct 20, 2004
 */
public class CLXMG extends megamek.common.weapons.mgs.MGWeapon {
    @java.io.Serial
    private static final long serialVersionUID = 2557645305248678454L;

    public CLXMG() {
        super();
        name = "Machine Gun (X)";
        setInternalName("CLXMG");
        addLookupName("Clan Machine Gun (X)");
        heat = 0;
        damage = 3;
        infDamageClass = megamek.common.equipment.WeaponType.WEAPON_BURST_6D6;
        rackSize = 2;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.25;
        criticalSlots = 1;
        bv = 5;
        cost = 5000;
        shortAV = 2;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        rulesRefs = "228, TM";
        techAdvancement.setTechBase(megamek.common.enums.TechBase.CLAN)
              .setIntroLevel(false)
              .setUnofficial(false)
              .setTechRating(megamek.common.enums.TechRating.C)
              .setAvailability(megamek.common.enums.AvailabilityValue.X, megamek.common.enums.AvailabilityValue.B, megamek.common.enums.AvailabilityValue.B, megamek.common.enums.AvailabilityValue.A)
              .setClanAdvancement(2821, 2825, 2830, DATE_NONE, DATE_NONE)
              .setClanApproximate(true, false, false, false, false)
              .setPrototypeFactions(megamek.common.enums.Faction.CSF)
              .setProductionFactions(megamek.common.enums.Faction.CSF);
    }
}
